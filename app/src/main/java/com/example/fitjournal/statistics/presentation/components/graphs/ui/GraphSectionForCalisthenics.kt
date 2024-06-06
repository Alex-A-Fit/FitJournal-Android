package com.example.fitjournal.statistics.presentation.components.graphs.ui

import androidx.compose.runtime.Composable
import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes
import kotlin.math.roundToInt

@Composable
fun GraphSectionForCalisthenics(
    graphData: GraphData.Calisthenics,
    calisthenicGraphs: GraphUiTypes.CalisthenicsGraphs
) {
    GraphSection(
        graphData = when (calisthenicGraphs) {
            GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> graphData.totalRepsToDate
            GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> graphData.totalWeightUsedToDate
        },
        stringForGraphPopUp = { x, y ->
            when (calisthenicGraphs) {
                GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> "Total Reps: ${y.roundToInt()} $x"
                GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> "Weight Used: $y $x"
            }
        },
        yAxisSuffixLabel = when (calisthenicGraphs) {
            GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> "reps"
            GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> "lbs"
        },
        convertYaxisValue = {
            return@GraphSection it.roundToInt()
        }
    )
}
