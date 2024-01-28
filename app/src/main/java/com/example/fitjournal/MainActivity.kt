package com.example.fitjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitjournal.navigation.NavigationInterface
import com.example.fitjournal.navigation.Route
import com.example.fitjournal.navigation.Route.APP_SCREEN
import com.example.fitjournal.navigation.Route.LOTTIE_INTRO
import com.example.fitjournal.screens.AppScreen
import com.example.fitjournal.screens.home.HomeScreen
import com.example.fitjournal.screens.lottie.LottieHomeScreenAnimation
import com.example.fitjournal.ui.theme.FitJournalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.runSplashScreen()
        setContent {
            FitJournalTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    NavHost(
                        navController = navController,
                        modifier = Modifier.fillMaxSize(),
                        startDestination = LOTTIE_INTRO
                    ) {
                        composable(APP_SCREEN) {
                            AppScreen(
                                modifier = Modifier,
                                navigateToDestination = { navigation ->
                                    navigateToDestination(navigationInterface = navigation)
                                }
                            )
                        }
                        composable(Route.WORKOUT_LIBRARY_SCREEN) {
                            HomeScreen(
                                text = "HomeScreen",
                                modifier = Modifier.padding()
                            )
                        }
                        composable(Route.HOME_SCREEN) {
                            HomeScreen(
                                modifier = Modifier,
                                text = "Home Screen"
                            )
                        }
                        composable(Route.WORKOUT_STATISTICS_SCREEN) {
                            HomeScreen(
                                modifier = Modifier,
                                text = "Workout Statistics Screen"
                            )
                        }
                        composable(LOTTIE_INTRO) {
                            LottieHomeScreenAnimation(
                                mainActivityState = mainViewModel.homeScreenState,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun navigateToDestination(navigationInterface: NavigationInterface){
    when (navigationInterface){
        NavigationInterface.NavigateToApp -> NavigationInterface.NavigateToApp
        NavigationInterface.NavigateToHome -> NavigationInterface.NavigateToHome
        NavigationInterface.NavigateToWorkoutLibrary -> NavigationInterface.NavigateToWorkoutLibrary
        NavigationInterface.NavigateToWorkoutStatistics -> NavigationInterface.NavigateToWorkoutStatistics
    }
}