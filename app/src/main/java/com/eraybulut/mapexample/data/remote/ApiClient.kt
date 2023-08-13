package com.eraybulut.mapexample.data.remote

import com.eraybulut.mapexample.util.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */

object ApiClient {
    fun getApiService() : ApiService {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        val gson = gsonBuilder.create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_MAP_URL)
            .client(
                OkHttpClient().newBuilder()
                    .connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constants.READ_TIMEOUT,TimeUnit.SECONDS)
                    .writeTimeout(Constants.WRITE_TIMEOUT,TimeUnit.SECONDS)
                    .build()
            )

            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiService::class.java)
    }
}