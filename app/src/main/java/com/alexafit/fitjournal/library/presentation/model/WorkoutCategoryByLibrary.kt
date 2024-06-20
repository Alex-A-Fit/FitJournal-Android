package com.alexafit.fitjournal.library.presentation.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alexafit.fitjournal.core.presentation.model.LibraryWorkoutItem
import com.alexafit.fitjournal.statistics.presentation.components.uistate.WorkoutName

data class WorkoutCategoryByLibrary(
    val name: String,
    val items: SnapshotStateList<LibraryWorkoutItem>
)

data class WorkoutCategoryByJournal(
    val name: String,
    val items: SnapshotStateList<WorkoutName>
)
