package com.example.fitjournal.core.util.filter

import androidx.compose.runtime.toMutableStateList
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategoryByJournal
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategoryByLibrary

fun searchForText(text: String, list: List<WorkoutCategoryByLibrary>): List<WorkoutCategoryByLibrary> {
    val filteredList = list.map { category ->
        WorkoutCategoryByLibrary(
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

fun searchForJournalEntry(text: String, list: List<WorkoutCategoryByJournal>): List<WorkoutCategoryByJournal> {
    val filteredList = list.map { category ->
        WorkoutCategoryByJournal(
            name = category.name,
            items =
            category.items.filter { workout ->
                val lowercaseWorkout = workout.workoutDetailsModel.name.lowercase()
                lowercaseWorkout.contains(text.lowercase())
            }.toMutableStateList()
        )
    }
    return filteredList.filterNot { it.items.isEmpty() }
}
