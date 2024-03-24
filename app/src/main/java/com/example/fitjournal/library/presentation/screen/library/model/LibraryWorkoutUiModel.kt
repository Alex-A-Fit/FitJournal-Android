package com.example.fitjournal.library.presentation.screen.library.model

data class LibraryWorkoutUiModel(
    val masterWorkoutList: List<LibraryWorkoutItem> = emptyList(),
    val listOfSearchedWorkouts: List<LibraryWorkoutItem> = emptyList(),
    val searchedTerm: String = "",
    val handleLibraryWorkoutClickEvents: (LibraryWorkoutClickEvents) -> Unit
)
