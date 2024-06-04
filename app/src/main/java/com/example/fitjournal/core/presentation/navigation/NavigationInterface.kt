package com.example.fitjournal.core.presentation.navigation

sealed interface NavigationInterface {
    data object NavigateToHome : NavigationInterface
    data object NavigateToWorkoutLibrary : NavigationInterface
    data object NavigateToWorkoutStatistics : NavigationInterface
    data class NavigateToEditWorkout(val workoutId: String) : NavigationInterface
    data class NavigateToAddWorkout(val workoutDate: String) : NavigationInterface
    data class NavigateToStatisticsDetails(val workoutName: String) : NavigationInterface
    data class NavigateToAddWorkoutDetails(
        val workoutName: String,
        val workoutType: String,
        val workoutDate: String
    ) : NavigationInterface
}
