package com.example.fitjournal.statistics.presentation.components.graphs.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes

@Composable
fun GraphSectionForCardio(
    graphData: GraphData.Cardio,
    cardioGraphs: GraphUiTypes.CardioGraphs
) {
    LineChartGraph(
        graphData = when (cardioGraphs) {
            GraphUiTypes.CardioGraphs.DISTANCE_OVER_DATE -> graphData.totalDistanceToDate
            GraphUiTypes.CardioGraphs.AVERAGE_SPEED_OVER_DATE -> graphData.averageSpeedToDate
        },
        stringForGraphPopUp = { x, y ->
            when (cardioGraphs) {
                GraphUiTypes.CardioGraphs.DISTANCE_OVER_DATE -> "Distance Traveled: $y mi $x"
                GraphUiTypes.CardioGraphs.AVERAGE_SPEED_OVER_DATE -> "Average Speed: $y mi/hr $x"
            }
        },
        yAxisSuffixLabel = when (cardioGraphs) {
            GraphUiTypes.CardioGraphs.DISTANCE_OVER_DATE -> stringResource(id = R.string.label_miles_acronym)
            GraphUiTypes.CardioGraphs.AVERAGE_SPEED_OVER_DATE -> stringResource(id = R.string.label_miles_per_hour_acronym)
        }
    )
}
