package com.example.randomcityapp.di


import com.example.randomcityapp.data.database.CityDao
import com.example.randomcityapp.data.repository.CityRepositoryImpl
import com.example.randomcityapp.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCityRepository(cityDao: CityDao): CityRepository {
        return CityRepositoryImpl(cityDao)
    }
}
