package com.example.fitjournal.statistics.presentation.components.graphs.title

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum
import com.example.fitjournal.statistics.presentation.components.text.GraphTitle
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes

@Composable
fun CalisthenicsGraphTitle(
    timeRangeEnum: TimeRangeEnum,
    graphDisplayedEnum: GraphUiTypes.CalisthenicsGraphs,
    updateGraphDisplayed: (GraphUiTypes.CalisthenicsGraphs) -> Unit

) {
    val graphDisplayed by rememberSaveable(graphDisplayedEnum) {
        mutableStateOf(graphDisplayedEnum)
    }

    when (graphDisplayed) {
        GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> {
            GraphTitle(
                timeRange = timeRangeEnum,
                text = "Reps Vs Date",
                showBackArrow = false,
                showNextArrow = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    ),
                onNextArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.CalisthenicsGraphs.TOTAL_TIME_OVER_DATE)
                }
            )
        }

        GraphUiTypes.CalisthenicsGraphs.TOTAL_TIME_OVER_DATE -> {
            GraphTitle(
                timeRange = timeRangeEnum,
                text = "Time Vs Date",
                showBackArrow = true,
                showNextArrow = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    ),
                onBackArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE)
                },
                onNextArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE)
                }
            )
        }

        GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> {
            GraphTitle(
                timeRange = timeRangeEnum,
                text = "Weight Vs Date",
                showBackArrow = true,
                showNextArrow = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    ),
                onBackArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.CalisthenicsGraphs.TOTAL_TIME_OVER_DATE)
                }
            )
        }
    }
}
