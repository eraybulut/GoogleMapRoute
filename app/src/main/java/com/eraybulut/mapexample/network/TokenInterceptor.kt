package com.eraybulut.mapexample.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by Eray BULUT on 15.08.2023
 * eraybulutlar@gmail.com
 */

class TokenInterceptor @Inject constructor() :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val modifiedHttpUrl = originalRequest.url.newBuilder()
            .addQueryParameter("key", "YOUR_API_KEY")
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(modifiedHttpUrl)
            .build()

        return chain.proceed(newRequest)
    }
}