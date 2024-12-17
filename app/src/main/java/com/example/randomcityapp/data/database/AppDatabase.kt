package com.example.randomcityapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}
