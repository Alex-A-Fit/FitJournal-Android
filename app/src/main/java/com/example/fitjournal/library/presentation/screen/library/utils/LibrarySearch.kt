package com.example.fitjournal.library.presentation.screen.library.utils

import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutItem
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory
import java.util.SortedMap

fun searchForText(text: String): SortedMap<Char, List<String>> {
    return MockData.libraryWorkoutList
        .filter {
            it.lowercase().contains(text.lowercase())
        }.groupBy {
            it.first()
        }.toSortedMap()
}

fun mapToLibraryUiList(workoutMap: SortedMap<Char, List<String>>): List<WorkoutCategory> {
    return workoutMap.map { workouts ->
        WorkoutCategory(
            name = workouts.key.toString(),
            items = workouts.value.map { workout ->
                LibraryWorkoutItem(
                    workoutName = workout,
                    workoutDetails = "Details for $workout go here"
                )
            }
        )
    }
}
