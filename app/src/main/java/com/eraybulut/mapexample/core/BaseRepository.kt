package com.eraybulut.mapexample.core

import com.eraybulut.mapexample.model.response.ErrorResponseModel
import com.eraybulut.mapexample.util.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

/**
 * Created by Eray BULUT on 15.08.2023
 * eraybulutlar@gmail.com
 */

abstract class BaseRepository {

    suspend fun <T> safeApiRequest(apiRequest : suspend () -> T): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkResult.Success(apiRequest.invoke())
            } catch (t: Throwable) {
                when(t){
                    is HttpException -> {
                        NetworkResult.Error(false,errorBodyParser(t.response()?.errorBody()?.string()))
                    }
                    else -> NetworkResult.Error(false,t.localizedMessage)
                }
            }
        }
    }
}

private fun errorBodyParser(error: String?): String {
    error?.let {
        return try {
            val errorResponse = Gson().fromJson(error, ErrorResponseModel::class.java)
            val errorMessage = errorResponse.message
            errorMessage
        } catch (e: Exception) {
            "Bir hata ile karsılasıldı"
        }
    }
    return "Bir hata ile karsılasıldı"
}