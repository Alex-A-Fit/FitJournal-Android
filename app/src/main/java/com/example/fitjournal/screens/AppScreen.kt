package com.example.fitjournal.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitjournal.components.BottomAppBar
import com.example.fitjournal.components.TopAppBar
import com.example.fitjournal.navigation.Route
import com.example.fitjournal.navigation.mainActivityNavigation
import com.example.fitjournal.screens.home.HomeScreen
import com.example.fitjournal.screens.home.HomeScreenTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(appBarTitle = { HomeScreenTitle() })
        },
        bottomBar = {
            BottomAppBar(
                navigate = { navRoute ->
                    mainActivityNavigation(
                        navController = navController,
                        navigationInterface = navRoute
                    )
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            startDestination = Route.LOTTIE_INTRO
        ) {
            composable(Route.WORKOUT_LIBRARY_SCREEN) {
                HomeScreen(
                    modifier = Modifier.padding(padding),
                    text = "Workout Library Screen"
                )
            }
            composable(Route.HOME_SCREEN) {
                HomeScreen(
                    modifier = Modifier.padding(padding),
                    text = "Home Screen"
                )
            }
            composable(Route.WORKOUT_STATISTICS_SCREEN) {
                HomeScreen(
                    modifier = Modifier.padding(padding),
                    text = "Workout Statistics Screen"
                )
            }
        }
    }
}