package com.example.randomcityapp.data.repository


import com.example.randomcityapp.data.database.CityDao
import com.example.randomcityapp.data.database.CityItem
import kotlinx.coroutines.flow.Flow

class CityRepository(private val cityDao: CityDao) {
    fun getAllCities(): Flow<List<CityItem>> = cityDao.getAllCities()
    suspend fun insertCity(city: CityItem) = cityDao.insertCity(city)
}
