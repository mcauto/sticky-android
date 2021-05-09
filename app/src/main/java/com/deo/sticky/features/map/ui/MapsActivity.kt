package com.deo.sticky.features.map.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.deo.sticky.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
            isIndoorEnabled = false
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
