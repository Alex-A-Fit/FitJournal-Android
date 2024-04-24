package com.example.fitjournal.journalEntry.screen.journalEntry

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fitjournal.core.Workouts
import com.example.fitjournal.journalEntry.domain.WorkoutDetail

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
