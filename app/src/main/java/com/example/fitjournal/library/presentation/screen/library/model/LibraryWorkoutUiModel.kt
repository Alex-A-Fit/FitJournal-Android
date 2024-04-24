package com.example.fitjournal.library.presentation.screen.library.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class LibraryWorkoutUiModel(
    val masterWorkoutList: List<WorkoutCategory> = emptyList(),
    val listOfSearchedWorkouts: SnapshotStateList<WorkoutCategory> = mutableStateListOf(),
    val searchedTerm: String = "",
    val handleLibraryWorkoutClickEvents: (LibraryWorkoutClickEvents) -> Unit
)
