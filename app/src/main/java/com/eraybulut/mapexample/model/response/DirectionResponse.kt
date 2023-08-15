package com.eraybulut.mapexample.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Eray BULUT on 13.08.2023
 * eraybulutlar@gmail.com
 */

data class DirectionResponse(
    @SerializedName("geocoded_waypoints")
    var geocodedWaypoints: List<GeocodedWaypoint>? = null,
    @SerializedName("routes")
    var routes: List<Route>? = null,
    @SerializedName("status")
    var status: String? = null
)

data class GeocodedWaypoint(
    @SerializedName("geocoder_status")
    var geocoderStatus: String? = null,
    @SerializedName("place_id")
    var placeId: String? = null,
    @SerializedName("types")
    var types: List<String>? = null
)

data class Route(
    @SerializedName("bounds")
    var bounds: Bounds? = null,
    @SerializedName("copyrights")
    var copyrights: String? = null,
    @SerializedName("legs")
    var legs: List<Leg>? = null,
    @SerializedName("overview_polyline")
    var overviewPolyline: OverviewPolyline? = null,
    @SerializedName("summary")
    var summary: String? = null,
)

data class OverviewPolyline(
    @SerializedName("points")
    var points: String? = null
)

data class Bounds(
    @SerializedName("northeast")
    var northeast: Northeast? = null,
    @SerializedName("southwest")
    var southwest: Southwest? = null
)

data class Northeast(
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("lng")
    var lng: Double? = null
)


data class Southwest(
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("lng")
    var lng: Double? = null
)

data class Leg(
    @SerializedName("distance")
    var distance: Distance? = null,
    @SerializedName("duration")
    var duration: Duration? = null,
    @SerializedName("end_address")
    var endAddress: String? = null,
    @SerializedName("end_location")
    var endLocation: EndLocation? = null,
    @SerializedName("start_address")
    var startAddress: String? = null,
    @SerializedName("start_location")
    var startLocation: StartLocation? = null,
    @SerializedName("steps")
    var steps: List<Step>? = null,
)

data class Distance(
    @SerializedName("text")
    var text: String? = null,
    @SerializedName("value")
    var value: Int? = null
)

data class Duration(
    @SerializedName("text")
    var text: String? = null,
    @SerializedName("value")
    var value: Int? = null
)

data class EndLocation(
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("lng")
    var lng: Double? = null
)

data class StartLocation(
    @SerializedName("lat")
    var lat: Double? = null,
    @SerializedName("lng")
    var lng: Double? = null
)

data class Step(
    @SerializedName("distance")
    var distance: Distance? = null,
    @SerializedName("duration")
    var duration: Duration? = null,
    @SerializedName("end_location")
    var endLocation: EndLocation? = null,
    @SerializedName("html_instructions")
    var htmlInstructions: String? = null,
    @SerializedName("maneuver")
    var maneuver: String? = null,
    @SerializedName("polyline")
    var polyline: Polyline? = null,
    @SerializedName("start_location")
    var startLocation: StartLocation? = null,
    @SerializedName("travel_mode")
    var travelMode: String? = null
)

data class Polyline(
    @SerializedName("points")
    var points: String? = null
)
