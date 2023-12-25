package com.eraybulut.mapexample.ui.selectdestination

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.eraybulut.mapexample.core.BaseViewModel
import com.eraybulut.mapexample.data.model.response.Prediction
import com.eraybulut.mapexample.data.onError
import com.eraybulut.mapexample.data.onSuccess
import com.eraybulut.mapexample.network.MapService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Eray BULUT on 16.08.2023
 * eraybulutlar@gmail.com
 */

@HiltViewModel
class SelectDestinationViewModel @Inject constructor(
    private val mapService: MapService
): BaseViewModel() {

    private val _address =
        MutableStateFlow<List<Prediction>>(emptyList())
    val address get() = _address.asStateFlow()


    fun searchLocation(query : String){
        viewModelScope.launch {
            safeApiCall {
                mapService.getAutoComplete(query)
            }.onSuccess {
               if (it.predictions != null){
                   _address.value = it.predictions
               }
            }.onError {
                Log.e("Select Destination Error",it)
            }
        }
    }

}
