package com.example.fitjournal.journalEntry.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.fitjournal.journalEntry.model.events.JournalEntryEvents
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory

data class JournalEntryUiModel(
    val masterWorkoutList: List<WorkoutCategory> = emptyList(),
    val listOfSearchedWorkouts: SnapshotStateList<WorkoutCategory> = mutableStateListOf(),
    val searchedTerm: String = "",
    val handleJournalEntryClickEvents: (JournalEntryEvents) -> Unit
)
