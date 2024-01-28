package com.example.fitjournal.navigation

import androidx.navigation.NavController

fun mainActivityNavigation(
    navigationInterface: NavigationInterface,
    navController: NavController
) {
    when (navigationInterface) {

        NavigationInterface.NavigateToHome -> {
            navController.navigate(Route.HOME_SCREEN)
        }

        NavigationInterface.NavigateToWorkoutLibrary -> {
            navController.navigate(Route.WORKOUT_LIBRARY_SCREEN)
        }

        NavigationInterface.NavigateToWorkoutStatistics -> {
            navController.navigate(Route.WORKOUT_STATISTICS_SCREEN)
        }

        NavigationInterface.NavigateToApp -> {
            navController.navigate(Route.APP_SCREEN)
        }
    }
}
