package com.example.randomcityapp

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.window.layout.WindowMetricsCalculator
import com.example.randomcityapp.data.database.AppDatabase
import com.example.randomcityapp.data.repository.CityRepository
import com.example.randomcityapp.navigation.AppNavigation
import com.example.randomcityapp.producer.CityProducer
import com.example.randomcityapp.ui.components.ExitConfirmationDialog
import com.example.randomcityapp.ui.components.SideBySideLayout
import com.example.randomcityapp.ui.screens.MainScreen
import com.example.randomcityapp.ui.theme.RandomCityAppTheme
import com.example.randomcityapp.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MainActivity : ComponentActivity() {
    private lateinit var cityProducerJob: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room database
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "city_database"
        ).build()

        val repository = CityRepository(database.cityDao())
//        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val mainViewModel = MainViewModel(repository)

        val cityProducer = CityProducer(repository)
        cityProducerJob = CoroutineScope(Dispatchers.IO)
        cityProducer.startProducing(cityProducerJob)

        setContent {
            RandomCityAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val windowMetrics = WindowMetricsCalculator.getOrCreate()
                    val currentMetrics = windowMetrics.computeCurrentWindowMetrics(this)
                    val windowWidthDp =
                        currentMetrics.bounds.width() / resources.displayMetrics.density

                    val isLandscape =
                        resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                    val isLargeScreen = windowWidthDp > 600
                    var showExitDialog by remember { mutableStateOf(false) }


                    BackHandler {
                        when (navController.currentBackStackEntry?.destination?.route) {
                            "main" -> {
                                showExitDialog = true
                            }
                            else -> {
                                navController.popBackStack()
                            }
                        }
                    }

                    if (showExitDialog) {
                        ExitConfirmationDialog(
                            onDismiss = { showExitDialog = false },
                            onConfirmExit = { finish() }
                        )
                    }

                    if (isLandscape || isLargeScreen) {
                        Log.d("geocod", "onCreate: Landscape ")
                        SideBySideLayout(
                            viewModel = mainViewModel
                        )
                    } else {
                        AppNavigation(navController, mainViewModel)
                    }
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("ConfigChange", "Orientation changed to: ${newConfig.orientation}")
    }

    override fun onDestroy() {
        super.onDestroy()
        cityProducerJob.cancel()
    }
}
