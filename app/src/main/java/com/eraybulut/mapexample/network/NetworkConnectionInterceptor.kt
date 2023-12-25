package com.eraybulut.mapexample.network

import android.content.Context
import com.eraybulut.mapexample.util.NetworkUtils.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Eray BULUT on 22.12.2023
 * eraybulutlar@gmail.com
 */

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (isNetworkAvailable(context).not()) {
            throw NoConnectionException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    inner class NoConnectionException : IOException() {
        override val message: String
            get() = super.message ?: "No Internet Connection"
    }
}