package com.example.fitjournal.statistics.presentation.components.graphs.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes

@Composable
fun GraphSectionForWeightLifting(
    graphData: GraphData.WeightTraining,
    weightTrainingGraphs: GraphUiTypes.WeightTrainingGraphs,
    modifier: Modifier = Modifier
) {
    LineChartGraph(
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
            GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE -> stringResource(id = R.string.label_pounds_acronym)
            GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE -> stringResource(id = R.string.label_pounds_acronym)
        },
        modifier = modifier
    )
}
