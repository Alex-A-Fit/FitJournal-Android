package com.alexafit.fitjournal.addWorkout.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alexafit.fitjournal.addWorkout.model.events.AddWorkoutEvents
import com.alexafit.fitjournal.library.presentation.model.WorkoutCategoryByLibrary

data class AddWorkoutUiModel(
    val masterWorkoutList: List<WorkoutCategoryByLibrary> = emptyList(),
    val listOfSearchedWorkouts: SnapshotStateList<WorkoutCategoryByLibrary> = mutableStateListOf(),
    val searchedTerm: String = "",
    val handleAddWorkoutClickEvents: (AddWorkoutEvents) -> Unit
)
