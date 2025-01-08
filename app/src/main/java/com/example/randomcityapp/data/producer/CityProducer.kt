package com.example.randomcityapp.producer

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.randomcityapp.data.database.CityItem
import com.example.randomcityapp.domain.repository.CityRepository
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CityProducer @Inject constructor(
    private val repository: CityRepository
) : DefaultLifecycleObserver {
    private val cities = listOf("New York", "Los Angeles", "Cincinnati", "Philadelphia", "NashVille", "Saint Louis", "Miami")
    private val colors = listOf("Purple", "Green", "Blue", "Red", "Black", "Grey")

    private var producerJob: Job? = null
    private val _isProducing = MutableStateFlow(false)
    val isProducing = _isProducing.asStateFlow()

    private var scope: CoroutineScope? = null

    fun startProducing(coroutineScope: CoroutineScope) {
        scope = coroutineScope
        Log.d("CityProducer", "startProducing called")
    }

    private fun startProducerJob() {
        if (_isProducing.value) {
            Log.d("CityProducer", "Already producing, skipping start")
            return
        }

        Log.d("CityProducer", "Starting producer job")
        _isProducing.value = true

        producerJob = scope?.launch {
            try {
                while (isActive && _isProducing.value) {
                    val city = cities.random()
                    val color = colors.random()
                    val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

                    Log.d("CityProducer", "Emitting new city: $city")
                    repository.insertCity(CityItem(city = city, color = color, dateTime = dateTime))
                    delay(2000L)
                }
            } catch (e: CancellationException) {
                Log.d("CityProducer", "Producer job cancelled")
                throw e
            } finally {
                _isProducing.value = false
                Log.d("CityProducer", "Producer job finished")
            }
        }
    }

    private fun stopProducerJob() {
        Log.d("CityProducer", "Stopping producer job")
        _isProducing.value = false
        producerJob?.cancel()
        producerJob = null
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d("CityProducer", "onResume")
        startProducerJob()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d("CityProducer", "onPause")
        stopProducerJob()
    }

    fun cleanup() {
        Log.d("CityProducer", "Cleanup called")
        stopProducerJob()
        scope = null
    }
}