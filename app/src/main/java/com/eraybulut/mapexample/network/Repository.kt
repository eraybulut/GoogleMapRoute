package com.eraybulut.mapexample.network

import com.eraybulut.mapexample.core.BaseRepository
import javax.inject.Inject

/**
 * Created by Eray BULUT on 15.08.2023
 * eraybulutlar@gmail.com
 */

class Repository @Inject constructor(private val apiService: ApiService) : BaseRepository() {

    suspend fun getMapRoute(
        origin: String,
        destination: String,
    ) = safeApiRequest {
        apiService.getMapRoute(
            origin = origin,
            destination = destination,
        )
    }
}