package com.example.fitjournal.library.presentation.screen.library.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.fitjournal.core.presentation.model.LibraryWorkoutItem

data class WorkoutCategory(
    val name: String,
    val items: SnapshotStateList<LibraryWorkoutItem>
)
