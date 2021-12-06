package com.deo.sticky.features.map.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.deo.sticky.R
import com.deo.sticky.base.BindableFragment
import com.deo.sticky.databinding.FragmentMapBinding
import com.deo.sticky.features.RequestKey
import com.deo.sticky.features.checkin.CategoryViewModel
import com.deo.sticky.features.checkin.CheckInViewModel
import com.deo.sticky.features.checkin.PlaceViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
internal class MapFragment :
    BindableFragment<FragmentMapBinding>(R.layout.fragment_map) {
    @Inject
    lateinit var locationClient: FusedLocationProviderClient

    @Inject
    lateinit var placesClient: PlacesClient

    @Inject
    lateinit var locationRequest: LocationRequest

    lateinit var googleMap: GoogleMap
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null

    private val _categoryViewModel: CategoryViewModel by activityViewModels()
    private val _placeViewModel: PlaceViewModel by activityViewModels()
    private val _checkInViewModel: CheckInViewModel by activityViewModels()

    // onCreateView 이후 callback
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkInViewModel = _checkInViewModel
            placeViewModel = _placeViewModel
            categoryViewModel = _categoryViewModel
        }

        initButtons()

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            this.googleMap = googleMap
            // MapFragment는 FragmentContainerView이므로 fragment의 컨테이너로서 동작
            // com.google.android.gms.maps.SupportMapFragment으로서 동작
            val gangNamCoffeeBean = LatLng(37.4988373, 127.0292686)

            googleMap.apply {
                setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireContext(), R.raw.uber_style
                    )
                )
                addMarker(
                    MarkerOptions()
                        .position(gangNamCoffeeBean)
                        .title("강남 12번 출구 커피빈")
                )
            }
            getLocationPermission()
            updateLocationUI()
            focusDevicePosition()
        }

        setFragmentResultListener(RequestKey.checkIn) { _, _ ->
            val category = _categoryViewModel.selectedCategory.value?.name
            val placeName = _placeViewModel.placeName.value
            val longitude = _placeViewModel.longitude.value
            val latitude = _placeViewModel.latitude.value
            Timber.w("checkIn: $category, $placeName, $longitude, $latitude")
            val timerAction: () -> Unit = {
                binding.recordingTime.text = _checkInViewModel.hhmmss.value
            }
            _checkInViewModel.timerStart(timerAction)
        }
    }

    /** 화면의 버튼 초기 설정 */
    private fun initButtons() {
        binding.apply {
            statistic.setOnClickListener {
                Timber.w("통계")
                val latitude = lastKnownLocation!!.latitude
                val longitude = lastKnownLocation!!.longitude
                _placeViewModel.getPlacesWithRadius(latitude = latitude, longitude = longitude)
            }
            setting.setOnClickListener {
                Timber.w("설정")
            }
            checkIn.setOnClickListener {
                focusDevicePosition()
                findNavController().navigate(MapFragmentDirections.actionMapFragmentToCheckInFragment())
                Timber.w("체크인")
            }
            here.setOnClickListener {
                focusDevicePosition()
            }
            checkOut.setOnClickListener {
                val dialog = CheckOutDialogFragment()
                dialog.show(parentFragmentManager, "CheckoutDialog")
            }
        }
    }

    /** 위치 결과가 비동기로 호출되어 멤버변수에 latitude와 langitude가 등록됨 */
    @SuppressLint("MissingPermission")
    private fun focusDevicePosition() {
        if (!locationPermissionGranted) {
            Timber.d("위치정보 권한이 없습니다.")
            return
        }
        locationClient.lastLocation.addOnCompleteListener { task ->
            if (!task.isSuccessful || task.result == null) {
                Timber.e("현재 위치를 찾을 수 없습니다.")
            } else {
                lastKnownLocation = task.result
                val latitude = lastKnownLocation!!.latitude
                val longitude = lastKnownLocation!!.longitude
                _placeViewModel.apply {
                    onLatitude(latitude)
                    onLongitude(longitude)
                }
                googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(latitude, longitude),
                        DEFAULT_ZOOM
                    )
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        try {
            googleMap.apply {
                isMyLocationEnabled = locationPermissionGranted
                uiSettings.isMyLocationButtonEnabled = false
                if (!locationPermissionGranted) {
                    isMyLocationEnabled = false
                    uiSettings.isMyLocationButtonEnabled = false
                    lastKnownLocation = null
                    getLocationPermission()
                }
            }
        } catch (e: SecurityException) {
            Timber.e(e)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 18f
    }
}
