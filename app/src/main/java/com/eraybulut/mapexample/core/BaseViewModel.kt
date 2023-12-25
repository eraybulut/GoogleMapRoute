package com.eraybulut.mapexample.core

import androidx.lifecycle.ViewModel
import com.eraybulut.mapexample.data.ResultWrapper
import com.eraybulut.mapexample.network.NetworkConnectionInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

/**
 * Created by Eray BULUT on 22.12.2023
 * eraybulutlar@gmail.com
 */

abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading get() = _loading.asStateFlow()

    fun showLoading() {
        _loading.value = true
    }

    fun hideLoading() {
        _loading.value = false
    }

    suspend fun <T> safeApiCall(apiRequest: suspend () -> T): ResultWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResultWrapper.Success(apiRequest.invoke())
            } catch (t: Throwable) {
                handleApiError(t)
            }
        }
    }

    private fun <T> handleApiError(throwable: Throwable): ResultWrapper<T> {
        return when (throwable) {
            is NetworkConnectionInterceptor.NoConnectionException -> {
                ResultWrapper.Error("İnternet Bağlantınızı Kontrol Ediniz")
            }

            is SocketTimeoutException -> {
                ResultWrapper.Error("Bağlantı Zaman Aşımına Uğradı")
            }

            else -> {
                ResultWrapper.Error("Bir Sorun Oluştu")
            }
        }
    }
}