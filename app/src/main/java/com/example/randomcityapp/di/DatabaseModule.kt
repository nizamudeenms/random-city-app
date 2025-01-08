package com.example.randomcityapp.di

import android.content.Context
import androidx.room.Room
import com.example.randomcityapp.data.database.AppDatabase
import com.example.randomcityapp.data.database.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "city_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCityDao(database: AppDatabase): CityDao {
        return database.cityDao()
    }
}