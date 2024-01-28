package com.example.fitjournal.navigation

sealed interface NavigationInterface {
    data object NavigateToHome : NavigationInterface
    data object NavigateToApp : NavigationInterface
    data object NavigateToWorkoutLibrary : NavigationInterface
    data object NavigateToWorkoutStatistics : NavigationInterface
}
