package com.eraybulut.mapexample.ui.placedetail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.eraybulut.mapexample.core.BaseViewModel
import com.eraybulut.mapexample.data.PrefDataSource
import com.eraybulut.mapexample.data.model.response.PlaceDetailsResponse
import com.eraybulut.mapexample.data.onError
import com.eraybulut.mapexample.data.onSuccess
import com.eraybulut.mapexample.network.MapService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Eray BULUT on 23.12.2023
 * eraybulutlar@gmail.com
 */
@HiltViewModel
class PlaceDetailViewModel @Inject constructor(
    private val mapService: MapService,
    private val prefDataSource: PrefDataSource
) : BaseViewModel() {

    private val _placeDetail =
        MutableStateFlow(PlaceDetailsResponse())
    val placeDetail get() = _placeDetail.asStateFlow()

    fun getPlaceDetail(placeId: String) {
        viewModelScope.launch {
            showLoading()
            safeApiCall {
                mapService.getPlaceDetails(placeId)
            }.onSuccess {
                hideLoading()
                if (it.status == "OK") {
                    _placeDetail.value = it
                }
            }.onError {
                hideLoading()
                Log.e("KEY-detail",it)
            }
        }
    }


}