package com.example.randomcityapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomcityapp.data.database.CityItem
import com.example.randomcityapp.domain.repository.CityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(private val repository: CityRepository) : ViewModel() {


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