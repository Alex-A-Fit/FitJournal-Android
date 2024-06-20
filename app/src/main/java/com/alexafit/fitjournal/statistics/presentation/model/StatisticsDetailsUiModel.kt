package com.alexafit.fitjournal.statistics.presentation.model

import com.alexafit.fitjournal.core.domain.model.WorkoutModel
import com.alexafit.fitjournal.core.util.state.UiState
import com.alexafit.fitjournal.statistics.domain.model.TimeRangeEnum
import com.alexafit.fitjournal.statistics.domain.model.WorkoutAnalytics

data class StatisticsDetailsUiModel(
    // realm db list
    val realmList: List<WorkoutModel> = emptyList(),

    // ui state for statistics details
    val workoutStatisticsDetailsUiState: UiState<WorkoutAnalytics> = UiState.None,

    // click events
    val handleStatisticsDetailsClickEvents: (StatisticsDetailsEvents) -> Unit,

    val weightTrainingGraphs: GraphUiTypes.WeightTrainingGraphs = GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE,
    val calisthenicGraphs: GraphUiTypes.CalisthenicsGraphs = GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE,
    val cardioGraphs: GraphUiTypes.CardioGraphs = GraphUiTypes.CardioGraphs.DISTANCE_OVER_DATE,

    val timeRangeEnum: TimeRangeEnum = TimeRangeEnum.WEEK
)
