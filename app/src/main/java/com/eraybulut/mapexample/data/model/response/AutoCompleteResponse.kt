package com.eraybulut.mapexample.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Eray BULUT on 16.08.2023
 * eraybulutlar@gmail.com
 */

data class AutoCompleteResponse(
    @SerializedName("predictions")
    val predictions: List<Prediction>? = null,
    @SerializedName("status")
    val status: String? = null
)

data class Prediction(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("place_id")
    val placeId: String? = null
)