package com.eraybulut.mapexample.util.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */


fun Context.getCityAndDistrictFromLatLng(
    latitude: Double, longitude: Double
): Pair<String?, String?> {
    val geocoder = android.location.Geocoder(this)
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    val cityName = addresses?.get(0)?.subAdminArea
    val districtName = addresses?.get(0)?.countryName
    return Pair(cityName, districtName)
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.setCall(phone: String) {
    try {
        Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }.also {
            startActivity(it)
        }
    }catch (e: Exception){
        showToast("Telefon uygulaması bulunamadı.")
    }
}

fun Context.openGoogleMaps(lat: String, lng: String) {
    if (lat.isNotBlank() && lng.isNotBlank()) {
        val gmmIntentUri = Uri.parse("google.navigation:q=$lat,$lng")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            setPackage("com.google.android.apps.maps")
        }

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            showToast("Google Maps uygulaması yüklü değil.")
        }
    } else {
        showToast("Geçersiz konum bilgisi.")
    }
}