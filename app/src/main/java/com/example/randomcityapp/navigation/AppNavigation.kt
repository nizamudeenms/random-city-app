package com.example.randomcityapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.randomcityapp.ui.components.ExitConfirmationDialog
import com.example.randomcityapp.ui.screen.SplashScreen
import com.example.randomcityapp.ui.screens.DetailsScreen
import com.example.randomcityapp.ui.screens.MainScreen
import com.example.randomcityapp.viewmodels.MainViewModel

@Composable
fun AppNavigation(navController: NavHostController, mainViewModel: MainViewModel) {
    val showExitDialog = remember { mutableStateOf(false) }

    BackHandler(enabled = navController.currentBackStackEntry?.destination?.route == "main") {
        showExitDialog.value = true
    }

    if (showExitDialog.value) {
        ExitConfirmationDialog(
            onDismiss = { showExitDialog.value = false },
            onConfirmExit = { System.exit(0) }
        )
    }

    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate("main") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable("main") {
            MainScreen(
                viewModel = mainViewModel,
                onCityClick = { city ->
                    navController.navigate("details/${city.city}/${city.color}")
                }
            )
        }
        composable("details/{city}/{color}",
            arguments = listOf(
                navArgument("city") { type = NavType.StringType },
                navArgument("color") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            DetailsScreen(
                city = backStackEntry.arguments?.getString("city") ?: "",
                color = backStackEntry.arguments?.getString("color") ?: ""
            )
        }
    }
}