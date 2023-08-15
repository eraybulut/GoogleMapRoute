package com.eraybulut.mapexample.util.extensions

import android.content.res.Resources
import android.graphics.Bitmap
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.eraybulut.mapexample.R
import com.eraybulut.mapexample.model.response.DirectionResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import retrofit2.Response

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */


fun GoogleMap.zoomArea(list: List<LatLng>) {
    if (list.isEmpty()) return
    val boundsBuilder = LatLngBounds.Builder()
    for (latLngPoint in list) boundsBuilder.include(latLngPoint)
    val routePadding = 225
    val latLngBounds = boundsBuilder.build()
    moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding))
}

fun GoogleMap.setStartingZoomArea(startLatLng: LatLng, endLatLng: LatLng) {
    val builder = LatLngBounds.Builder()
    builder.include(startLatLng)
    builder.include(endLatLng)
    val bounds = builder.build()
    val padding = 10
    this.setLatLngBoundsForCameraTarget(bounds)
    this.moveCamera(
        CameraUpdateFactory.newLatLngBounds(
            bounds,
            displayWidth(),
            displayHeight(),
            padding
        )
    )
    this.setMinZoomPreference(this.cameraPosition.zoom)
}

fun GoogleMap.changeCameraPosition(latLng: LatLng, zoom: Float = 15f) {
    moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
}

fun GoogleMap.drawPolyline(response: DirectionResponse,@ColorRes colorId : Int) {
    val shape = response.routes?.get(0)?.overviewPolyline?.points
    val polyline = PolylineOptions()
        .addAll(PolyUtil.decode(shape))
        .width(10f)
        .color(colorId)
    addPolyline(polyline)
}

fun GoogleMap.addMarker(position: LatLng, title: String, icon: Bitmap) {
    addMarker(
        MarkerOptions()
            .position(position)
            .title(title)
            .icon(BitmapDescriptorFactory.fromBitmap(icon))
    )
}

fun displayWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun displayHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}
