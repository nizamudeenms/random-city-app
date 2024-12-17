package com.example.randomcityapp.network

import retrofit2.http.GET
import retrofit2.http.Query

data class GeocodingResponse(
    val results: List<Result>
) {
    data class Result(
        val geometry: Geometry
    ) {
        data class Geometry(
            val location: Location
        ) {
            data class Location(
                val lat: Double,
                val lng: Double
            )
        }
    }
}

interface GeoCodingAPIService {
    @GET("geocode/json")
    suspend fun getCoordinates(
        @Query("address") cityName: String,
        @Query("key") apiKey: String
    ): GeocodingResponse
}
