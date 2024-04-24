package com.example.fitjournal.library.presentation.screen.library.model

import com.example.fitjournal.core.presentation.model.LibraryWorkoutItem

data class WorkoutCategory(
    val name: String,
    val items: List<LibraryWorkoutItem>
)
