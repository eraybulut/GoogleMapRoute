package com.eraybulut.mapexample.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eraybulut.mapexample.model.response.DirectionResponse
import com.eraybulut.mapexample.network.Repository
import com.eraybulut.mapexample.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

/**
 * Created by Eray BULUT on 15.08.2023
 * eraybulutlar@gmail.com
 */

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _route = MutableStateFlow(DirectionResponse())
    val route get() = _route.asStateFlow()

    fun drawRoute(origin: String, destination: String) {
        viewModelScope.launch {
            when (val route = repository.getMapRoute(origin, destination)) {
                is NetworkResult.Success -> {
                    _route.value = route.data as DirectionResponse
                }
                is NetworkResult.Error -> {}
            }
        }
    }
}