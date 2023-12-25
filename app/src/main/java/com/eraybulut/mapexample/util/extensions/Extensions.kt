package com.eraybulut.mapexample.util.extensions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.eraybulut.mapexample.R
import com.eraybulut.mapexample.util.Constants.BASE_IMAGE_URL
import com.eraybulut.mapexample.util.Constants.REQUEST_LOCATION_PERMISSION
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Eray BULUT on 22.12.2023
 * eraybulutlar@gmail.com
 */

fun EditText.textChangesToFlow(): Flow<String> = callbackFlow {
    val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun afterTextChanged(query: Editable?) {
            trySend(query.toString())
        }

    }
    addTextChangedListener(watcher)
    awaitClose { removeTextChangedListener(watcher) }
}

fun <T> LifecycleOwner.collectLast(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(action)
        }
    }
}

fun Activity.requestLocationPermissions(onRequestPermissionsResult: (Boolean) -> Unit) {
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


fun String?.orUndefined() = this ?: "Undefined"
fun View.getString(@StringRes resId: Int): String = resources.getString(resId)

fun Int.asColor(context: Context) = ContextCompat.getColor(context, this)

fun LatLng?.orEmptyLatLng() = this?:LatLng(0.0, 0.0)

fun ImageView.loadUrl(url : String){
    val placeHolder = createPlaceHolder(context)
    load("${BASE_IMAGE_URL}${url}&key=${getString(R.string.google_maps_key)}"){
        crossfade(true)
        error(R.drawable.ic_map)
        crossfade(500)
        placeholder(placeHolder)
    }
}


private fun createPlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 12f
        centerRadius = 40f
        start()
    }
}

fun EditText.focusAndShowKeyboard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}
