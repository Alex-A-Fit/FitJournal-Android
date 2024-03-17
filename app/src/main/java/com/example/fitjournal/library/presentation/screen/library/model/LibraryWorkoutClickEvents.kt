package com.example.fitjournal.library.presentation.screen.library.model

sealed class LibraryWorkoutClickEvents {
    data class UpdateSearchBarText(val text: String) : LibraryWorkoutClickEvents()
    object ClearSearchBarText : LibraryWorkoutClickEvents()
}
