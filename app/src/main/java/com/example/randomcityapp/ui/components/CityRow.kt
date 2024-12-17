package com.example.randomcityapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomcityapp.data.database.CityItem

@Composable
fun CityRow(city: CityItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween // Align items to opposite ends
    ) {
        Text(
            text = city.city,
            color = Color(android.graphics.Color.parseColor(city.color)), // Set the color dynamically
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
        )
        Text(
            text = city.dateTime,
            color = Color(android.graphics.Color.parseColor(city.color)), // Set the color dynamically
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
        )
    }
}

