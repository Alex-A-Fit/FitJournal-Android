package com.alexafit.fitjournal.core.presentation.navigation

import androidx.navigation.NavController

fun navigationEvent(
    navigationInterface: NavigationInterface,
    navController: NavController
) {
    when (navigationInterface) {
        NavigationInterface.NavigateToHome -> {
            navController.popBackStack()
            navController.navigate(Route.HOME_SCREEN)
        }

        NavigationInterface.NavigateToWorkoutLibrary -> {
            navController.navigate(Route.WORKOUT_LIBRARY_SCREEN)
        }

        NavigationInterface.NavigateToWorkoutStatistics -> {
            navController.navigate(Route.WORKOUT_STATISTICS_SCREEN)
        }

        is NavigationInterface.NavigateToAddWorkout -> {
            navController.navigate(
                "${Route.ADD_WORKOUT_SCREEN}/${navigationInterface.workoutDate}"
            )
        }

        is NavigationInterface.NavigateToEditWorkout -> {
            navController.navigate(
                route = "${Route.EDIT_JOURNAL_SCREEN}/${navigationInterface.workoutId}"
            )
        }

        is NavigationInterface.NavigateToAddWorkoutDetails -> {
            navController.navigate("${Route.ADD_WORKOUT_DETAILS_SCREEN}/${navigationInterface.workoutName}/${navigationInterface.workoutType}/${navigationInterface.workoutDate}")
        }

        is NavigationInterface.NavigateToStatisticsDetails -> {
            navController.navigate("${Route.WORKOUT_STATISTICS_DETAILS_SCREEN}/${navigationInterface.workoutName}")
        }

        NavigationInterface.NavigateToOnboarding -> {
            navController.navigate(Route.ONBOARDING_SCREEN)
        }
    }
}
