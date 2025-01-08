package com.example.randomcityapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.randomcityapp.BuildConfig
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.randomcityapp.network.RetrofitInstance
import com.example.randomcityapp.workers.CityToastWorker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(city: String, color: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // State to hold the coordinates
    var cityCoordinates by remember {
        mutableStateOf(
            LatLng(
                37.7749,
                -122.4194
            )
        )
    } // Default to San Francisco

    // Camera position state
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityCoordinates, 12f)
    }

    // Fetch coordinates using the city name
    LaunchedEffect(city) {
        cityCoordinates =
            fetchCoordinatesForCity(cityName = city, apiKey = BuildConfig.MAPS_API_KEY)
    }

    // Update camera position when coordinates change
    LaunchedEffect(cityCoordinates) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(cityCoordinates, 12f)
    }

    val parsedColor = try {
        Color(android.graphics.Color.parseColor(color))
    } catch (e: IllegalArgumentException) {
        androidx.compose.ui.graphics.Color.Gray
    }

    LaunchedEffect(city) {
        val workRequest = OneTimeWorkRequestBuilder<CityToastWorker>()
            .setInputData(workDataOf("cityName" to city))
            .setInitialDelay(1, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(city) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = parsedColor
                )
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                )
            }
        }
    )
}

// Function to fetch coordinates for a given city using Retrofit
suspend fun fetchCoordinatesForCity(cityName: String, apiKey: String): LatLng {
    Log.d("GeocodingResponse", "Response: $cityName")


    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getCoordinates(cityName, apiKey)
            Log.d("GeocodingResponse", "Response: $response")

            val location = response.results.firstOrNull()?.geometry?.location
            if (location != null) {
                Log.d(
                    "GeocodingSuccess",
                    "Coordinates for $cityName: ${location.lat}, ${location.lng}"
                )
                LatLng(location.lat, location.lng)
            } else {
                Log.e("GeocodingError", "No results found for $cityName")
                LatLng(37.7749, -122.4194) // Default to San Francisco on failure
            }
        } catch (e: Exception) {
            Log.e("GeocodingError", "Error fetching coordinates: ${e.message}")
            LatLng(37.7749, -122.4194) // Default on error
        }
    }
}
