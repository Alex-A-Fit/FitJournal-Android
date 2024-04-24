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

        NavigationInterface.NavigateToJournalEntryDetails -> {
            navController.navigate(Route.JOURNAL_ENTRY_DETAILS)
        }

        NavigationInterface.NavigateToEditWorkout -> {
            navController.navigate(Route.EDIT_JOURNAL_SCREEN)
        }
    }
}
