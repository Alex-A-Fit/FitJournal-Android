package com.example.fitjournal.navigation

import androidx.navigation.NavController

fun navigationEvent(
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
    }
}
