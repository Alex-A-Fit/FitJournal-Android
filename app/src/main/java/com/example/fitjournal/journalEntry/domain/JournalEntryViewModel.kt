package com.example.fitjournal.journalEntry.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fitjournal.core.Workouts

class JournalEntryViewModel : ViewModel() {

    private val workoutList = Workouts.availableWorkouts

    var selectedWorkoutDetail: MutableState<WorkoutDetail?> = mutableStateOf(null)

    fun searchWorkout(searchValue: String): List<WorkoutDetail> {
        return if (searchValue.isNotEmpty()) {
            workoutList.filter { it.workoutName.contains(searchValue, ignoreCase = true) }
        } else {
            workoutList
        }
    }

    fun save() {
    }
}
