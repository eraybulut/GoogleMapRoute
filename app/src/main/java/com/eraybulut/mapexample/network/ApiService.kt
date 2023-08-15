package com.eraybulut.mapexample.network

import com.eraybulut.mapexample.model.response.DirectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */

interface ApiService {

    @GET("maps/api/directions/json")
    suspend fun getMapRoute(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
    ): DirectionResponse
}