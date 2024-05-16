package com.example.fitjournal.core.presentation.navigation

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

        NavigationInterface.NavigateToJournalEntry -> {
            navController.navigate(Route.JOURNAL_ENTRY_SCREEN)
        }

        is NavigationInterface.NavigateToEditWorkout -> {
            navController.navigate(
                route = "${Route.EDIT_JOURNAL_SCREEN}/${navigationInterface.workoutId}"
            )
        }

        is NavigationInterface.NavigateToJournalEntryDetails -> {
            navController.navigate("${Route.JOURNAL_ENTRY_DETAILS}/${navigationInterface.workoutName}/${navigationInterface.workoutType}")
        }
    }
}
