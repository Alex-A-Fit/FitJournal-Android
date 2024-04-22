package com.example.fitjournal.statistics.domain.model

import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.model.ui.WorkoutUiModel

data class StatisticsScreenState(
    val workoutList: UiState<List<WorkoutUiModel>> = UiState.None,
    val workoutModelList: List<WorkoutModel> = emptyList()
)
