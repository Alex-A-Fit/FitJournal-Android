package com.example.fitjournal.library.presentation.screen.library.utils

import androidx.compose.runtime.toMutableStateList
import com.example.fitjournal.core.domain.model.WorkoutLibraryModel
import com.example.fitjournal.core.presentation.model.LibraryWorkoutItem
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory
import java.util.SortedMap

fun mapToLibraryUiList(workoutMap: SortedMap<String, List<WorkoutLibraryModel>>): List<WorkoutCategory> {
    return workoutMap.map { workouts ->
        WorkoutCategory(
            name = workouts.key.toString(),
            items = workouts.value.map { workout ->
                LibraryWorkoutItem(
                    workoutName = workout.name,
                    workoutTypeEnum = workout.workoutTypeEnum
                )
            }.toMutableStateList()
        )
    }
}
