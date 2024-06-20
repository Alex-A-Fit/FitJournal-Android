package com.alexafit.fitjournal.statistics.domain.mapper

import androidx.compose.runtime.toMutableStateList
import com.alexafit.fitjournal.core.domain.model.WorkoutModel
import com.alexafit.fitjournal.library.presentation.model.WorkoutCategoryByJournal
import java.util.SortedMap

fun mapToStatisticsUiList(workoutMap: SortedMap<String, List<WorkoutModel>>): List<WorkoutCategoryByJournal> {
    return workoutMap.map { workouts ->
        WorkoutCategoryByJournal(
            name = workouts.key.toString(),
            items = workouts.value.distinctBy { it.workoutDetailsModel.name }.map {
                it.workoutDetailsModel.name
            }.toMutableStateList()
        )
    }
}
