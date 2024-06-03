package com.example.fitjournal.addWorkout.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.fitjournal.addWorkout.model.events.AddWorkoutEvents
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategoryByLibrary

data class AddWorkoutUiModel(
    val masterWorkoutList: List<WorkoutCategoryByLibrary> = emptyList(),
    val listOfSearchedWorkouts: SnapshotStateList<WorkoutCategoryByLibrary> = mutableStateListOf(),
    val searchedTerm: String = "",
    val handleJournalEntryClickEvents: (AddWorkoutEvents) -> Unit
)
