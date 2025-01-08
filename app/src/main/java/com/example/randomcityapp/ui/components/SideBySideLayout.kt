package com.example.randomcityapp.ui.components

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.randomcityapp.data.database.CityItem
import com.example.randomcityapp.ui.screen.MainScreen
import com.example.randomcityapp.ui.screens.DetailsScreen
import com.example.randomcityapp.viewmodels.MainViewModel

@Composable
fun SideBySideLayout(
    viewModel: MainViewModel
) {
    var selectedCity by rememberSaveable { mutableStateOf<CityItem?>(null) }
    var showExitDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    BackHandler {
        showExitDialog = true
    }

    // Exit Confirmation Dialog
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit App") },
            text = { Text("Are you sure you want to exit the app?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        (context as? Activity)?.finish()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }

    Row(modifier = Modifier.fillMaxSize()) {
        // Main Screen
        MainScreen(
            viewModel = viewModel,
            onCityClick = { city ->
                selectedCity = city
            },
            modifier = Modifier.weight(1f),
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            selectedCity?.let { city ->
                DetailsScreen(
                    city = city.city,
                    color = city.color
                )
            } ?: run {
                Text("Select a city to view details", modifier = Modifier.align(Alignment.Center))
            }
        }

    }
}
