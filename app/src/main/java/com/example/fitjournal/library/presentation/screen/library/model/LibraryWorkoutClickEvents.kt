package com.example.fitjournal.library.presentation.screen.library.model

import android.content.Context
import com.example.fitjournal.core.presentation.model.LibraryWorkoutItem
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

sealed class LibraryWorkoutClickEvents {
    data class UpdateSearch(val text: String) : LibraryWorkoutClickEvents()
    data object ClearSearch : LibraryWorkoutClickEvents()

    data class WorkoutItemClicked(
        val libraryWorkoutItem: LibraryWorkoutItem,
        val workoutCategoryIndex: Int
    ) : LibraryWorkoutClickEvents()

    data class UpdateLibraryWorkout(
        val workoutName: String,
        val workoutTypeEnum: WorkoutTypeEnum,
        val onSuccessCallback: () -> Unit,
        val onErrorCallback: () -> Unit
    ) : LibraryWorkoutClickEvents()

    data class DeleteLibraryWorkout(
        val onSuccessCallback: suspend (String) -> Unit,
        val context: Context,
        val onErrorCallback: suspend (String) -> Unit
    ) : LibraryWorkoutClickEvents()
}
