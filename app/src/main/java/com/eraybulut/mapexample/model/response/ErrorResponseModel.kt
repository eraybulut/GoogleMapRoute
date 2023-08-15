package com.eraybulut.mapexample.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Eray BULUT on 15.08.2023
 * eraybulutlar@gmail.com
 */

data class ErrorResponseModel (
    @SerializedName("message")
    val message : String
)