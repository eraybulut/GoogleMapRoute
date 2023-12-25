package com.eraybulut.mapexample.data.model.response

/**
 * Created by Eray BULUT on 23.12.2023
 * eraybulutlar@gmail.com
 */

import com.google.gson.annotations.SerializedName

data class PlaceDetailsResponse(
    @SerializedName("result")
    val result: PlaceDetailsResult? = null,
    @SerializedName("status")
    val status: String? = null
)

data class PlaceDetailsResult(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("formatted_address")
    val address: String? = null,
    @SerializedName("formatted_phone_number")
    val phoneNumber: String? = null,
    @SerializedName("geometry")
    val geometry: Geometry? = null,
    @SerializedName("opening_hours")
    val openingHours: OpeningHours? = null,
    @SerializedName("photos")
    val photos: List<Photo>? = null,
    @SerializedName("icon")
    val icon: String? = null,
)

data class Geometry(
    @SerializedName("location")
    val location: Location? = null
)

data class Location(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)

data class OpeningHours(
    @SerializedName("open_now")
    val openNow: Boolean? = null,
    @SerializedName("periods")
    val periods: List<Period>? = null,
    @SerializedName("weekday_text")
    val weekdayText: List<String>? = null
)

data class Period(
    @SerializedName("close")
    val close: Close? = null,
    @SerializedName("open")
    val open: Open? = null
)

data class Close(
    @SerializedName("day")
    val day: Int? = null,
    @SerializedName("time")
    val time: String? = null
)

data class Open(
    @SerializedName("day")
    val day: Int? = null,
    @SerializedName("time")
    val time: String? = null
)

data class Photo(
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("html_attributions")
    val htmlAttributions: List<String>? = null,
    @SerializedName("photo_reference")
    val photoReference: String? = null,
    @SerializedName("width")
    val width: Int? = null
)
