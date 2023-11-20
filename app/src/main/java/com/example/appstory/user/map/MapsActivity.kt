package com.example.appstory.user.map

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.appstory.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.appstory.databinding.ActivityMapsBinding
import com.example.appstory.user.ViewModelFactory
import com.example.appstory.user.landing.LandingPage

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapsViewModel.getSession().observe(this) { result ->
            if (!result.isLogin) {
                startActivity(Intent(this, LandingPage::class.java))
                finish()
            } else {
                mapsViewModel.getStoryLocation(result.token)

                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true


        mMap.clear()

        addMarker()
    }

    private fun addMarker() {
        mapsViewModel.listStoryLocation.observe(this) {
            it?.listStory?.forEach { story ->
                val latLng = LatLng(story.lat, story.lon)
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(story.name)
                        .snippet(story.description)
                )
            }
        }
    }
}
