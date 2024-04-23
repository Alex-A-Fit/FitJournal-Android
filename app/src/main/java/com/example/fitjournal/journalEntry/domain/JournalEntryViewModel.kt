package com.example.fitjournal.journalEntry.domain

import androidx.lifecycle.ViewModel
import com.example.fitjournal.core.Workouts

class JournalEntryViewModel : ViewModel() {

    private val workoutList = Workouts.availableWorkouts
    fun searchWorkout(searchValue: String): List<WorkoutDetail> {
        return if (searchValue.isNotEmpty()) {
            workoutList.filter { it.workoutName.contains(searchValue, ignoreCase = true) }
        } else {
            workoutList
        }
    }
}
