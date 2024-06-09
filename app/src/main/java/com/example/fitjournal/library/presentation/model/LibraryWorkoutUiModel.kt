package com.example.fitjournal.library.presentation.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class LibraryWorkoutUiModel(
    val masterWorkoutList: List<WorkoutCategoryByLibrary> = emptyList(),
    val listOfSearchedWorkouts: SnapshotStateList<WorkoutCategoryByLibrary> = mutableStateListOf(),
    val searchedTerm: String = "",
    val libraryWorkoutClickEvent: (LibraryWorkoutClickEvents) -> Unit,
    val workoutItemDialogUiModel: WorkoutItemDialogUiModel = WorkoutItemDialogUiModel()
)
