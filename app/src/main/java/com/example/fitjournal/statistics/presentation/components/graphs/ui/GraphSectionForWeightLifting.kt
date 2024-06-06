package com.example.fitjournal.statistics.presentation.components.graphs.ui

import androidx.compose.runtime.Composable
import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes

@Composable
fun GraphSectionForWeightLifting(
    graphData: GraphData.WeightTraining,
    weightTrainingGraphs: GraphUiTypes.WeightTrainingGraphs
) {
    GraphSection(
        graphData = when (weightTrainingGraphs) {
            GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE -> graphData.topWeightToDate
            GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE -> graphData.mostVolumeToDate
        },
        stringForGraphPopUp = { x, y ->
            when (weightTrainingGraphs) {
                GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE -> "Weight Lifted: $y lbs $x"
                GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE -> "Workout Volume: $y lbs $x"
            }
        },
        yAxisSuffixLabel = when (weightTrainingGraphs) {
            GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE -> "lbs"
            GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE -> "lbs"
        }
    )
}
