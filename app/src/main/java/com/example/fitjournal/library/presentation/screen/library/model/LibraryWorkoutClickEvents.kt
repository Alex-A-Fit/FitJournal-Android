package com.example.fitjournal.library.presentation.screen.library.model

sealed class LibraryWorkoutClickEvents {
    data class UpdateSearch(val text: String) : LibraryWorkoutClickEvents()
    data object ClearSearch : LibraryWorkoutClickEvents()
}
