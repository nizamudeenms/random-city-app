package com.example.randomcityapp.utils

import java.util.Collections

object CityUtils {
    fun sortCitiesByName(cities: List<String>): List<String> {
        Collections.sort(cities)
        return cities
    }
}