package com.example.randomcityapp.producer

import com.example.randomcityapp.data.database.CityItem
import com.example.randomcityapp.data.repository.CityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*



class CityProducer(private val repository: CityRepository) {
    private val cities = listOf("New York", "Los Angeles", "Cincinnati", "Philadelphia", "NashVille", "Saint Louis", "Miami")
    private val colors = listOf("Purple", "Green", "Blue", "Red", "Black", "Grey")

    fun startProducing(cityProducerJob: CoroutineScope) {
        cityProducerJob.launch {
            val random = Random() // Use a random instance for better randomness control
            while (true) {
                val city = cities[random.nextInt(cities.size)]
                val color = colors[random.nextInt(colors.size)]
                val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                repository.insertCity(CityItem(city = city, color = color, dateTime = dateTime))
                delay(2000L)
            }
        }
    }
}
