package com.example.fitjournal.statistics.presentation.model

import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum
import com.example.fitjournal.statistics.domain.model.WorkoutAnalytics

data class StatisticsDetailsUiModel(
    // realm db list
    val realmList: List<WorkoutModel> = emptyList(),

    // ui state for statistics details
    val workoutStatisticsDetailsUiState: UiState<WorkoutAnalytics> = UiState.None,

    // click events
    val handleStatisticsDetailsClickEvents: (StatisticsDetailsEvents) -> Unit,

    val timeRangeEnum: TimeRangeEnum = TimeRangeEnum.WEEK
)
