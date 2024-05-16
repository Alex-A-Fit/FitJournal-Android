package com.example.fitjournal.core.util.filter

import androidx.compose.runtime.toMutableStateList
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory

fun searchForText(text: String, list: List<WorkoutCategory>): List<WorkoutCategory> {
    val filteredList = list.map { category ->
        WorkoutCategory(
            name = category.name,
            items =
            category.items.filter { workout ->
                val lowercaseWorkout = workout.workoutName.lowercase()
                lowercaseWorkout.contains(text.lowercase())
            }.toMutableStateList()
        )
    }
    return filteredList.filterNot { it.items.isEmpty() }
}
