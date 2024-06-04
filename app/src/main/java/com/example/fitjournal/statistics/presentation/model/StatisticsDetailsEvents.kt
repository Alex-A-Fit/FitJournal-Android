package com.example.fitjournal.statistics.presentation.model

import com.example.fitjournal.statistics.domain.model.TimeRangeEnum

sealed class StatisticsDetailsEvents {
    data class GetWorkoutStats(val workoutName: String) : StatisticsDetailsEvents()
    data class UpdateTimeRange(
        val timeRangeEnum: TimeRangeEnum
    ) : StatisticsDetailsEvents()
}
