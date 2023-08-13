package com.eraybulut.mapexample.util

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.eraybulut.mapexample.R
import com.eraybulut.mapexample.data.remote.ApiClient
import com.eraybulut.mapexample.model.response.DirectionResponse
import com.eraybulut.mapexample.util.extensions.getColorRes
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */

class DrawMapRoute(
    private val context: Context,
    private val map: GoogleMap,
    private val startPoint: LatLng,
    private val endPoint: LatLng
) {

    private val distance = MutableLiveData<String>()
    private val time = MutableLiveData<String>()

    fun drawRoute() {
        val apiServices = ApiClient.getApiService()
        apiServices.getDirection(
            "${startPoint.latitude},${startPoint.longitude}",
            "${endPoint.latitude},${endPoint.longitude}",
            context.getString(R.string.google_maps_key)
        )
            .enqueue(object : Callback<DirectionResponse> {
                override fun onResponse(
                    call: Call<DirectionResponse>,
                    response: Response<DirectionResponse>
                ) {
                    try {
                        distance.value =
                            response.body()!!.routes!![0].legs!![0].distance!!.text!!.replace(
                                "m",
                                "M"
                            ).replace("km", "KM")
                        time.value =
                            response.body()!!.routes!![0].legs!![0].duration!!.text!!.replace(
                                "hours",
                                "saat"
                            ).replace("mins", "dakika")
                                .replace("days", "gün").replace("secs", "saniye")
                        drawPolyline(response)
                    } catch (e: Exception) {
                        distance.value = "Tanımsız"
                        time.value = "Tanımsız"
                    }
                }

                override fun onFailure(call: Call<DirectionResponse>, t: Throwable) {}
            })
    }

    fun removeRoute() {
        distance.value = ""
        time.value = ""
        map.clear()
    }

    private fun drawPolyline(response: Response<DirectionResponse>) {
        val shape = response.body()?.routes?.get(0)?.overviewPolyline?.points
        val polyline = PolylineOptions()
            .addAll(PolyUtil.decode(shape))
            .width(10f)
            .color(context.getColorRes(R.color.green))
        map.addPolyline(polyline)
    }

    fun getDistance(): MutableLiveData<String> {
        return distance
    }

    fun getTime(): MutableLiveData<String> {
        return time
    }
}