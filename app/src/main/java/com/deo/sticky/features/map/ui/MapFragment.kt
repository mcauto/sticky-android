package com.deo.sticky.features.map.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deo.sticky.R
import com.deo.sticky.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
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
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_map) as? SupportMapFragment

        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val gangNamCoffeeBean = LatLng(37.4988373, 127.0292686)
        val gangNamStation = LatLng(37.497952, 127.027619)

        googleMap.apply {
            setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context, R.raw.uber_style
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
}
