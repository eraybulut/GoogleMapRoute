package com.eraybulut.mapexample.util.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */

fun Context.shareText(message: String) {
    Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }.also { intent ->
        startActivity(intent)
    }
}

fun Context.smallMarker(@DrawableRes image: Int, width: Int = 200, height: Int = 200): Bitmap? {
    val bitmapDraw = ContextCompat.getDrawable(this, image) as BitmapDrawable
    val bitmap = bitmapDraw.bitmap
    return Bitmap.createScaledBitmap(bitmap, width, height, false)
}

fun Context.vibrate(duration: Long) {
    val vibrator = ContextCompat.getSystemService(this, Vibrator::class.java)
    if (vibrator?.hasVibrator() == true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    duration, VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION") vibrator.vibrate(duration)
        }
    }
}

fun Context.getColorRes(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)


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