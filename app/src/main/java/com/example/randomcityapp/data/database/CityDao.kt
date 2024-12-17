package com.example.randomcityapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityItem)

    @Query("SELECT * FROM CityItem ORDER BY city ASC")
    fun getAllCities(): Flow<List<CityItem>>
}
