package com.example.randomcityapp.data.database


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val color: String,
    val dateTime: String
)
