package com.alexafit.fitjournal.statistics.presentation.model

import com.alexafit.fitjournal.statistics.domain.model.TimeRangeEnum

sealed class StatisticsDetailsEvents {
    data class UpdateTimeRange(val timeRangeEnum: TimeRangeEnum) : StatisticsDetailsEvents()
    data class UpdateWeightTrainingGraphShown(val graphToShow: GraphUiTypes.WeightTrainingGraphs) : StatisticsDetailsEvents()
    data class UpdateCalisthenicsGraphShown(val graphToShow: GraphUiTypes.CalisthenicsGraphs) : StatisticsDetailsEvents()
    data class UpdateCardioGraphShown(val graphToShow: GraphUiTypes.CardioGraphs) : StatisticsDetailsEvents()
}
