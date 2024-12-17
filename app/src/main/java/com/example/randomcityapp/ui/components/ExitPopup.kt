package com.example.randomcityapp.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.BackHandler

@Composable
fun ExitConfirmationDialog(
    onDismiss: () -> Unit = {},
    onConfirmExit: () -> Unit = {}
) {
    var showExitDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    // Handle back press
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
                        onConfirmExit()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onDismiss()
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}