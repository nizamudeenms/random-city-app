package com.example.randomcityapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomcityapp.data.database.CityItem
import com.example.randomcityapp.data.repository.CityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CityRepository) : ViewModel() {


    private val _cityList = MutableStateFlow<List<CityItem>>(emptyList())
    val cityList: StateFlow<List<CityItem>> get()= _cityList

    init {
        fetchCityData()
    }

    private fun fetchCityData() {
        viewModelScope.launch {
            repository.getAllCities().collect{cities->
                _cityList.value = cities

            }
        }
    }


}