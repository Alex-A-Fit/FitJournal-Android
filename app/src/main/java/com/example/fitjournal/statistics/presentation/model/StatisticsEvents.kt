package com.example.fitjournal.statistics.presentation.model

import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum

sealed class StatisticsEvents {
    data object ClearSearchBarFilter : StatisticsEvents()
    data class FilterSearchByWorkout(val workout: String) : StatisticsEvents()
    data class GetWorkoutStats(val workoutName: String) : StatisticsEvents()
    data class UpdateTimeRange(
        val timeRangeEnum: TimeRangeEnum,
        val workoutStats: List<WorkoutModel>
    ) : StatisticsEvents()
}