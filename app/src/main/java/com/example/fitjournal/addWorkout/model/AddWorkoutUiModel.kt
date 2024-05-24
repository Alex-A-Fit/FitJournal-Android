package com.example.fitjournal.addWorkout.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.fitjournal.addWorkout.model.events.AddWorkoutEvents
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory

data class AddWorkoutUiModel(
    val masterWorkoutList: List<WorkoutCategory> = emptyList(),
    val listOfSearchedWorkouts: SnapshotStateList<WorkoutCategory> = mutableStateListOf(),
    val searchedTerm: String = "",
    val handleJournalEntryClickEvents: (AddWorkoutEvents) -> Unit
)
