package com.eraybulut.mapexample.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.eraybulut.mapexample.R
import com.eraybulut.mapexample.core.BaseAppCompatActivity
import com.eraybulut.mapexample.databinding.ActivityMainBinding
import com.eraybulut.mapexample.ui.selectdestination.SelectDestinationActivity
import com.eraybulut.mapexample.util.Constants.REQUEST_LOCATION_PERMISSION
import com.eraybulut.mapexample.util.DrawMapRoute
import com.eraybulut.mapexample.util.extensions.addMarker
import com.eraybulut.mapexample.util.extensions.changeCameraPosition
import com.eraybulut.mapexample.util.extensions.setStartingZoomArea
import com.eraybulut.mapexample.util.extensions.showToast
import com.eraybulut.mapexample.util.extensions.smallMarker
import com.eraybulut.mapexample.util.extensions.zoomArea
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import io.nlopez.smartlocation.SmartLocation

class HomeActivity : BaseAppCompatActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
),
    OnMapReadyCallback {

    private var supportMapFragment: SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermissions { isGranted ->
            if (isGranted) {
                setupMap()
            } else {
                showToast("Konum izinleri verilmedi")
            }
        }
    }

    override fun setListeners() {
        with(binding) {
            txtSelectDestination.setOnClickListener {
                startActivity(Intent(this@HomeActivity, SelectDestinationActivity::class.java))
            }
        }
    }

    private fun setupMap() {
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        supportMapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showToast("Konum izinleri verilmedi")
            return
        }

        with(map) {
            setStartingZoomArea(
                startLatLng = LatLng(42.216071, 26.389816), endLatLng = LatLng(36.690183, 44.747969)
            )

            setOnMapLoadedCallback {
                isMyLocationEnabled = true
                SmartLocation.with(this@HomeActivity).location().start { location ->
                    map.changeCameraPosition(LatLng(location.latitude, location.longitude))
                    drawRoute(
                        map = map, startLocation = LatLng(location.latitude, location.longitude)
                    )
                }
            }
        }
    }

    private fun drawRoute(map: GoogleMap, startLocation: LatLng) {
        map.zoomArea(listOf(startLocation, LatLng(40.9834373, 28.7309099)))

        val startMarker = this.smallMarker(R.drawable.ic_start, width = 100, height = 100)
        val endMarker = this.smallMarker(R.drawable.ic_destination, width = 100, height = 100)

        map.addMarker(startLocation, "başlangıç", startMarker!!)

        map.addMarker(LatLng(40.9834373, 28.7309099), "bitiş", endMarker!!)

        DrawMapRoute(
            context = this,
            map = map,
            startPoint = startLocation,
            endPoint = LatLng(40.9834373, 28.7309099),
        ).apply {
            drawRoute()

            getTime().observe(this@HomeActivity) { time ->
                binding.txtTime.text = time
            }

            getDistance().observe(this@HomeActivity) { distance ->
                binding.txtDistance.text = distance
            }
        }
    }

    private fun requestLocationPermissions(onRequestPermissionsResult: (Boolean) -> Unit) {
        val coarsePermissionGranted = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val finePermissionGranted = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (coarsePermissionGranted && finePermissionGranted) {
            onRequestPermissionsResult(true)
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), REQUEST_LOCATION_PERMISSION
            )
        }
    }
}