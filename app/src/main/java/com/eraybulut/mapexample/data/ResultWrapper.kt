package com.eraybulut.mapexample.data

/**
 * Created by Eray BULUT on 22.12.2023
 * eraybulutlar@gmail.com
 */

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val message: String) : ResultWrapper<Nothing>()
}

fun <T> ResultWrapper<T>.onSuccess(action: (T) -> Unit): ResultWrapper<T> {
    return when (this) {
        is ResultWrapper.Error -> this
        is ResultWrapper.Success -> {
            action.invoke(value)
            this
        }
    }
}

fun <T> ResultWrapper<T>.onError(action: (String) -> Unit): ResultWrapper<T> {
    return when (this) {
        is ResultWrapper.Success -> this
        is ResultWrapper.Error -> {
            action.invoke(message)
            this
        }
    }
}
