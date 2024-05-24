package com.example.fitjournal.addWorkout.model.events

sealed class AddWorkoutEvents {
    data object ClearSearchBarFilter : AddWorkoutEvents()
    data class FilterSearchByWorkout(val workout: String) : AddWorkoutEvents()
}
