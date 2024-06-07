package com.example.fitjournal.statistics.presentation.components.graphs.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes
import kotlin.math.roundToInt

@Composable
fun GraphSectionForCalisthenics(
    graphData: GraphData.Calisthenics,
    calisthenicGraphs: GraphUiTypes.CalisthenicsGraphs
) {
    LineChartGraph(
        graphData = when (calisthenicGraphs) {
            GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> graphData.totalRepsToDate
            GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> graphData.totalWeightUsedToDate
        },
        stringForGraphPopUp = { x, y ->
            when (calisthenicGraphs) {
                GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> "Total Reps: ${y.roundToInt()} $x"
                GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> "Weight Used: $y lbs $x"
            }
        },
        yAxisSuffixLabel = when (calisthenicGraphs) {
            GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> stringResource(id = R.string.label_reps).lowercase()
            GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> stringResource(id = R.string.label_pounds_acronym)
        },
        convertYaxisValue = {
            return@LineChartGraph it.roundToInt()
        }
    )
}
