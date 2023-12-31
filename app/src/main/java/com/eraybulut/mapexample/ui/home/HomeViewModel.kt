package com.eraybulut.mapexample.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.eraybulut.mapexample.core.BaseViewModel
import com.eraybulut.mapexample.data.model.response.DirectionResponse
import com.eraybulut.mapexample.data.onError
import com.eraybulut.mapexample.data.onSuccess
import com.eraybulut.mapexample.network.MapService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Eray BULUT on 15.08.2023
 * eraybulutlar@gmail.com
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mapService: MapService
) : BaseViewModel() {

    private val _route = MutableStateFlow(DirectionResponse())
    val route get() = _route.asStateFlow()

    fun getMapRoute(origin: String, destination: String) {
        viewModelScope.launch {
            showLoading()
            safeApiCall {
                mapService.getMapRoute(origin, destination)
            }.onSuccess {
                _route.value = it
                hideLoading()
            }.onError {
                Log.e("HomeViewModel", "drawRoute: $it")
                hideLoading()
            }
        }
    }
}