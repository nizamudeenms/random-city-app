package com.example.randomcityapp.domain.repository


import com.example.randomcityapp.data.database.CityItem
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun getAllCities(): Flow<List<CityItem>>
    suspend fun insertCity(city: CityItem)
}
