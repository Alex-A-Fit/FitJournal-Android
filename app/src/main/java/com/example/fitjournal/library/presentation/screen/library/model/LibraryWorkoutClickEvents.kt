package com.example.fitjournal.library.presentation.screen.library.model

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

sealed class LibraryWorkoutClickEvents {
    data class UpdateSearch(val text: String) : LibraryWorkoutClickEvents()
    data object ClearSearch : LibraryWorkoutClickEvents()

    data class WorkoutItemClicked(
        val workoutName: String,
        val workoutTypeEnum: WorkoutTypeEnum
    ) : LibraryWorkoutClickEvents()
}
