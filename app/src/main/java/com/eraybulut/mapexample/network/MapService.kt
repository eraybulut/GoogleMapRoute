package com.eraybulut.mapexample.network

import com.eraybulut.mapexample.data.model.response.AutoCompleteResponse
import com.eraybulut.mapexample.data.model.response.DirectionResponse
import com.eraybulut.mapexample.data.model.response.PlaceDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */

interface MapService {
    @GET("directions/json")
    suspend fun getMapRoute(
        @Query("origin") origin: String,
        @Query("destination") destination: String
    ): DirectionResponse

    @GET("place/autocomplete/json")
    suspend fun getAutoComplete(
        @Query("input") input: String,
    ): AutoCompleteResponse

    @GET("place/details/json")
    suspend fun getPlaceDetails(
        @Query("place_id") placeId: String,
    ): PlaceDetailsResponse
}