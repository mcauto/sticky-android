package com.deo.sticky.features.map.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
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
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
internal class MapFragment : BindableFragment<FragmentMapBinding>(R.layout.fragment_map) {
    private val requestCode = 1

    private val permissions =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    private lateinit var currentLocation: Location

    @Inject
    lateinit var locationClient: FusedLocationProviderClient

    @Inject
    lateinit var locationRequest: LocationRequest

    lateinit var googleMap: GoogleMap

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
            val gangNamStation = LatLng(37.497952, 127.027619)
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
                addMarker(
                    MarkerOptions()
                        .position(gangNamStation)
                        .title("강남역")
                )
                moveCamera(CameraUpdateFactory.newLatLngZoom(gangNamCoffeeBean, 18f))
            }
        }
        setFragmentResultListener(RequestKey.checkIn) { _, _ ->
            val category = _categoryViewModel.selectedCategory.value?.name
            val placeName = _placeViewModel.placeName.value
            Timber.w("checkIn: $category, $placeName")
        }
    }

    // 화면의 버튼 초기 설정
    private fun initButtons() {
        binding.apply {
            statistic.setOnClickListener {
                Timber.w("통계")
            }
            setting.setOnClickListener {
                Timber.w("설정")
            }
            checkIn.setOnClickListener {
                findNavController().navigate(MapFragmentDirections.actionMapFragmentToCheckInFragment())
                Timber.w("체크인")
            }
            here.setOnClickListener {
                Timber.w("현재 위치")
                if (
                    !checkMapPermission(requireContext()) &&
                    shouldShowRequestPermissionRationale(permissions.toString())
                ) {
                    ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode)
                }
                locationClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            for (location in locationResult.locations) {
                                if (location != null) {
                                    currentLocation = location
                                    val latitude = location.latitude
                                    val longitude = location.longitude
                                    Timber.w(
                                        "GPS Location changed, Latitude: $latitude, Longitude: $longitude"
                                    )
                                }
                            }
                        }
                    },
                    Looper.getMainLooper()
                )
            }
            checkOut.setOnClickListener {
                _checkInViewModel.onCheckOut()
            }
        }
    }

    private fun checkMapPermission(context: Context): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }
}
