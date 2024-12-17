package com.example.randomcityapp.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.randomcityapp.data.database.CityItem
import com.example.randomcityapp.viewmodels.MainViewModel
import com.example.randomcityapp.ui.components.CityRow

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onCityClick: (CityItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val cityList = viewModel.cityList.collectAsState() // Observe the city list
    val listState = rememberLazyListState()

    if (cityList.value.isEmpty()) {
        // Show a message if the list is empty
        EmptyStateMessage()
    } else {
        LazyColumn(
            modifier = modifier,
            state = listState
        ) {
            items(cityList.value) { city ->
                CityRow(city = city, onClick = { onCityClick(city) })
            }
        }

        LaunchedEffect(cityList.value.size) {
            listState.animateScrollToItem(cityList.value.lastIndex)
        }
    }
}

@Composable
fun EmptyStateMessage() {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text("No cities to display")
    }
}
