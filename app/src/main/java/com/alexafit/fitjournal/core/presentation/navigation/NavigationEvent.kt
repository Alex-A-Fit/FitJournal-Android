package com.alexafit.fitjournal.core.presentation.navigation

import androidx.navigation.NavController

fun navigationEvent(
    navigationDirectionInterface: NavigationDirectionInterface,
    navController: NavController
) {
    when (navigationDirectionInterface) {
        NavigationDirectionInterface.NavigateToHome -> {
            navController.popBackStack()
            navController.navigate(Route.HOME_SCREEN)
        }

        NavigationDirectionInterface.NavigateToWorkoutLibrary -> {
            navController.navigate(Route.WORKOUT_LIBRARY_SCREEN)
        }

        NavigationDirectionInterface.NavigateToWorkoutStatistics -> {
            navController.navigate(Route.WORKOUT_STATISTICS_SCREEN)
        }

        is NavigationDirectionInterface.NavigateToAddWorkout -> {
            navController.navigate(
                "${Route.ADD_WORKOUT_SCREEN}/${navigationDirectionInterface.workoutDate}"
            )
        }

        is NavigationDirectionInterface.NavigateToEditWorkout -> {
            navController.navigate(
                route = "${Route.EDIT_JOURNAL_SCREEN}/${navigationDirectionInterface.workoutId}"
            )
        }

        is NavigationDirectionInterface.NavigateToAddWorkoutDetails -> {
            navController.navigate("${Route.ADD_WORKOUT_DETAILS_SCREEN}/${navigationDirectionInterface.workoutName}/${navigationDirectionInterface.workoutType}/${navigationDirectionInterface.workoutDate}")
        }

        is NavigationDirectionInterface.NavigateToStatisticsDetails -> {
            navController.navigate("${Route.WORKOUT_STATISTICS_DETAILS_SCREEN}/${navigationDirectionInterface.workoutName}")
        }

        NavigationDirectionInterface.NavigateToOnboarding -> {
            navController.navigate(Route.ONBOARDING_SCREEN)
        }
    }
}
