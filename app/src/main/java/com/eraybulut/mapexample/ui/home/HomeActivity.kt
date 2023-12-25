package com.eraybulut.mapexample.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.eraybulut.mapexample.R
import com.eraybulut.mapexample.core.BaseActivity
import com.eraybulut.mapexample.data.PrefDataSource
import com.eraybulut.mapexample.data.model.response.DirectionResponse
import com.eraybulut.mapexample.data.model.response.SelectedAddressModel
import com.eraybulut.mapexample.databinding.ActivityMainBinding
import com.eraybulut.mapexample.ui.selectdestination.SelectDestinationActivity
import com.eraybulut.mapexample.util.extensions.addMarkerExt
import com.eraybulut.mapexample.util.extensions.asColor
import com.eraybulut.mapexample.util.extensions.changeCameraPosition
import com.eraybulut.mapexample.util.extensions.collectLast
import com.eraybulut.mapexample.util.extensions.drawPolyline
import com.eraybulut.mapexample.util.extensions.orEmptyLatLng
import com.eraybulut.mapexample.util.extensions.requestLocationPermissions
import com.eraybulut.mapexample.util.extensions.setCustomMapStyle
import com.eraybulut.mapexample.util.extensions.setStartingZoomArea
import com.eraybulut.mapexample.util.extensions.zoomArea
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding, HomeViewModel>(
    ActivityMainBinding::inflate
), OnMapReadyCallback {

    override val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var prefDataSource: PrefDataSource

    private var supportMapFragment: SupportMapFragment? = null
    private var mMap: GoogleMap? = null
    private var myCurrentLocation: LatLng? = null

    override fun onCreateFinished() {
        prefDataSource.clear()
        initMap()
        requestLocationPermissions {
            if (it) {
                startLocationUpdates()
            }
        }
    }

    override fun initializeListeners() {
        with(binding) {
            txtSelectDestination.setOnClickListener {
                startActivity(Intent(this@HomeActivity, SelectDestinationActivity::class.java))
            }
        }
    }

    override fun observeEvents() {
        with(viewModel) {
            collectLast(route, ::setupUi)
        }
    }

    private fun setupUi(directionResponse: DirectionResponse) {
        directionResponse.routes?.firstOrNull()?.legs?.firstOrNull()?.let { leg ->
            binding.apply {
                txtSelectDestination.text = leg.endAddress.orEmpty()
                txtDistance.text = leg.distance?.text.orEmpty()
                txtDuration.text = leg.duration?.text.orEmpty()
            }
            drawPolyline(directionResponse.routes?.firstOrNull()?.overviewPolyline?.points.orEmpty())
        }
    }

    private fun drawPolyline(shape: String) {
        mMap?.drawPolyline(
            shape = shape, colorId = R.color.green.asColor(this@HomeActivity)
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupLocationPermissions()
        setupMap()

        with(googleMap) {
            setCustomMapStyle(
                context = this@HomeActivity, styleResId = R.raw.map_style
            )

            setStartingZoomArea(
                startLatLng = TURKEY_START_LOCATION, TURKEY_END_LOCATION
            )
        }
    }

    private fun initMap() {
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        supportMapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    private fun setupLocationPermissions() {
        if (!hasLocationPermissions()) {
            return
        }
        mMap?.isMyLocationEnabled = true
    }

    private fun hasLocationPermissions() = ActivityCompat.checkSelfPermission(
        this@HomeActivity, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this@HomeActivity, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun setupMap() {
        mMap?.setOnMapLoadedCallback {
            requestLocationPermissions {
                startLocationUpdates()
            }
        }
    }

    private fun startLocationUpdates() {
        SmartLocation.with(this@HomeActivity).location().start { location ->
            updateMapLocation(location)
            myCurrentLocation = LatLng(location.latitude, location.longitude)
        }
    }

    private fun updateMapLocation(location: Location) {
        mMap?.changeCameraPosition(LatLng(location.latitude, location.longitude))
    }

    private fun drawRouteFromCurrentLocationToSelectedAddress(selectedAddress: LatLng?){
        sendRouteToServer(selectedAddress)
        mMap?.apply {
            clear()
            zoomArea(
                listOf(myCurrentLocation.orEmptyLatLng(),selectedAddress.orEmptyLatLng())
            )
            addMarkerExt(selectedAddress.orEmptyLatLng())
        }
    }

    private fun sendRouteToServer(selectedAddress: LatLng?){
        if (myCurrentLocation == null || selectedAddress == null) return
        viewModel.getMapRoute(
            origin = "${myCurrentLocation?.latitude},${myCurrentLocation?.longitude}",
            destination = "${selectedAddress.latitude},${selectedAddress.longitude}"
        )
    }

    override fun onResume() {
        super.onResume()
        val selectedAddress  = prefDataSource.getData(SelectedAddressModel::class.java)

        myCurrentLocation?.let {
            selectedAddress?.let { selectedAddress ->
                drawRouteFromCurrentLocationToSelectedAddress(selectedAddress.latLng)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        prefDataSource.clear()
    }

    companion object{
        private val TURKEY_START_LOCATION = LatLng(42.216071, 26.389816)
        private val TURKEY_END_LOCATION = LatLng(42.216071, 26.389816)
    }
}