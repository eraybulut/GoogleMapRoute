package com.eraybulut.mapexample.util

/**
 * Created by Eray BULUT on 15.08.2023
 * eraybulutlar@gmail.com
 */

sealed class NetworkResult<T>(
    val data :T? = null,
    val message : String? = null,
    val status : Boolean? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(status: Boolean?,message: String?): NetworkResult<T>(data = null, message = message, status = status)
}