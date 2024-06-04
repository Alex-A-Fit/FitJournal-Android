package com.example.fitjournal.library.presentation.screen.library.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.fitjournal.core.presentation.model.LibraryWorkoutItem
import com.example.fitjournal.statistics.presentation.components.uistate.WorkoutName

data class WorkoutCategoryByLibrary(
    val name: String,
    val items: SnapshotStateList<LibraryWorkoutItem>
)

data class WorkoutCategoryByJournal(
    val name: String,
    val items: SnapshotStateList<WorkoutName>
)
