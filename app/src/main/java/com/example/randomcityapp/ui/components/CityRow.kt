package com.example.randomcityapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
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

@Preview(showBackground = true, showSystemUi = true)
@PreviewScreenSizes
@Composable
private fun CityRowPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(), // Make the Box take the entire screen
        contentAlignment = Alignment.Center // Center the content both horizontally and vertically
    ) {

        CityRow(CityItem(city = "New York", dateTime = "20-10-2000", id = 1, color = "red")) { }
    }
}