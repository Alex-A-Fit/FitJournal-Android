package com.example.fitjournal.core.presentation.navigation

sealed interface NavigationInterface {
    data object NavigateToHome : NavigationInterface
    data object NavigateToWorkoutLibrary : NavigationInterface
    data object NavigateToWorkoutStatistics : NavigationInterface
    data object NavigateToJournalEntry : NavigationInterface
    data object NavigateToJournalEntryDetails : NavigationInterface
}
