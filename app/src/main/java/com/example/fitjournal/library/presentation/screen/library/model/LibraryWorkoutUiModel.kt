package com.example.fitjournal.library.presentation.screen.library.model

data class LibraryWorkoutUiModel(
    val masterWorkoutList: List<WorkoutCategory> = emptyList(),
    val listOfSearchedWorkouts: List<WorkoutCategory> = emptyList(),
    val searchedTerm: String = "",
    val handleLibraryWorkoutClickEvents: (LibraryWorkoutClickEvents) -> Unit
)
