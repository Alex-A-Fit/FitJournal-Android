package com.alexafit.fitjournal.core.presentation.navigation

sealed interface NavigationDirectionInterface {
    data object NavigateToHome : NavigationDirectionInterface
    data object NavigateToWorkoutLibrary : NavigationDirectionInterface
    data object NavigateToWorkoutStatistics : NavigationDirectionInterface
    data object NavigateToOnboarding : NavigationDirectionInterface
    data class NavigateToEditWorkout(val workoutId: String) : NavigationDirectionInterface
    data class NavigateToAddWorkout(val workoutDate: String) : NavigationDirectionInterface
    data class NavigateToStatisticsDetails(val workoutName: String) : NavigationDirectionInterface
    data class NavigateToAddWorkoutDetails(
        val workoutName: String,
        val workoutType: String,
        val workoutDate: String
    ) : NavigationDirectionInterface
}
