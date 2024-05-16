package com.example.fitjournal.core.presentation.navigation

sealed interface NavigationInterface {
    data object NavigateToHome : NavigationInterface
    data class NavigateToEditWorkout(val workoutId: String) : NavigationInterface
    data object NavigateToWorkoutLibrary : NavigationInterface
    data object NavigateToWorkoutStatistics : NavigationInterface
    data object NavigateToJournalEntry : NavigationInterface
    data class NavigateToJournalEntryDetails(
        val workoutName: String,
        val workoutType: String
    ) : NavigationInterface
}
