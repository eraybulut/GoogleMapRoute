package com.eraybulut.mapexample.util.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.util.Log
import androidx.annotation.ColorRes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */

fun GoogleMap.zoomArea(list: List<LatLng>,padding : Int = 225) {
    if (list.isEmpty()) return
    val boundsBuilder = LatLngBounds.Builder()
    for (latLngPoint in list) boundsBuilder.include(latLngPoint)
    val latLngBounds = boundsBuilder.build()
    moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, padding))
}

fun GoogleMap.setCustomMapStyle(context: Context, styleResId: Int) {
    try {
        val styleOptions = MapStyleOptions.loadRawResourceStyle(context, styleResId)
        this.setMapStyle(styleOptions)
    } catch (e: Resources.NotFoundException) {
        Log.e("Custom Style", "Can't find style. Error: ", e)
    }
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

fun GoogleMap.drawPolyline(shape: String, @ColorRes colorId: Int, with: Float = 10f) {
    if (shape.isEmpty()) return
    shape.let {
        val polyline = PolylineOptions().addAll(PolyUtil.decode(shape)).width(with).geodesic(true)
            .color(colorId)
        addPolyline(polyline)
    }
}
fun GoogleMap.addMarkerExt(position: LatLng) {
    addMarker(
        MarkerOptions()
            .position(position)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
    )
}

fun displayWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

fun displayHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}
