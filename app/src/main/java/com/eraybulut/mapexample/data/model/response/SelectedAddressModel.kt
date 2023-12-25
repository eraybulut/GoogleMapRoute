package com.eraybulut.mapexample.data.model.response

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Eray BULUT on 23.12.2023
 * eraybulutlar@gmail.com
 */

data class SelectedAddressModel(
    val address : String,
    val latLng: LatLng
)
