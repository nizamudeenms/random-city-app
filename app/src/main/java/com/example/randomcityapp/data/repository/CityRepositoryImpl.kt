package com.example.randomcityapp.data.repository

import com.example.randomcityapp.data.database.CityDao
import com.example.randomcityapp.data.database.CityItem
import com.example.randomcityapp.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(private val cityDao: CityDao) : CityRepository {
    override fun getAllCities(): Flow<List<CityItem>> = cityDao.getAllCities()
    override suspend fun insertCity(city: CityItem) = cityDao.insertCity(city)
}