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
            GraphUiTypes.CalisthenicsGraphs.TOTAL_TIME_OVER_DATE -> graphData.totalTimeToDate
            GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> graphData.totalWeightUsedToDate
        },
        stringForGraphPopUp = { x, y ->
            when (calisthenicGraphs) {
                GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> "Total Reps: ${y.roundToInt()} $x"
                GraphUiTypes.CalisthenicsGraphs.TOTAL_TIME_OVER_DATE -> "Total Time: ${y.div(60.0).roundToInt()}min $x"
                GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> "Weight Used: $y $x"
            }
        },
        yAxisSuffixLabel = when (calisthenicGraphs) {
            GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> "reps"
            GraphUiTypes.CalisthenicsGraphs.TOTAL_TIME_OVER_DATE -> "min"
            GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> "lbs"
        },
        convertYaxisValue = {
            if (calisthenicGraphs == GraphUiTypes.CalisthenicsGraphs.TOTAL_TIME_OVER_DATE) {
                return@GraphSection it.div(60.0).roundToInt()
            } else {
                return@GraphSection it.roundToInt()
            }
        }
    )
}
