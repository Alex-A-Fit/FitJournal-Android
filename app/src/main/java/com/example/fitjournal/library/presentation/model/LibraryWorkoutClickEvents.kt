package com.example.fitjournal.library.presentation.model

import android.content.Context
import com.example.fitjournal.core.presentation.model.LibraryWorkoutItem
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.library.domain.model.AddWorkoutToLibraryModel

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
        val context: Context,
        val onSuccessCallback: suspend (String) -> Unit,
        val onErrorCallback: suspend (String) -> Unit
    ) : LibraryWorkoutClickEvents()

    data class DeleteLibraryWorkout(
        val context: Context,
        val onSuccessCallback: suspend (String) -> Unit,
        val onErrorCallback: suspend (String) -> Unit
    ) : LibraryWorkoutClickEvents()

    data object SyncRealmWorkoutEntryFromDb : LibraryWorkoutClickEvents()

    data class AddWorkoutToLibrary(
        val workout: AddWorkoutToLibraryModel,
        val showSnackBar: suspend (String) -> Unit,
        val context: Context
    ) : LibraryWorkoutClickEvents()
}
