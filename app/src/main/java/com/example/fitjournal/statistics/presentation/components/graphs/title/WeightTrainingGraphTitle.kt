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
fun WeightTrainingGraphTitle(
    timeRangeEnum: TimeRangeEnum,
    graphDisplayedEnum: GraphUiTypes.WeightTrainingGraphs,
    updateGraphDisplayed: (GraphUiTypes.WeightTrainingGraphs) -> Unit
) {
    val graphDisplayed by rememberSaveable(graphDisplayedEnum) {
        mutableStateOf(graphDisplayedEnum)
    }

    when (graphDisplayed) {
        GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE -> {
            GraphTitle(
                timeRange = timeRangeEnum,
                text = "Weight Lifted",
                showBackArrow = false,
                showNextArrow = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    ),
                onNextArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE)
                }
            )
        }

        GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE -> {
            GraphTitle(
                timeRange = timeRangeEnum,
                text = "Total Volume",
                showBackArrow = true,
                showNextArrow = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    ),
                onBackArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE)
                }
            )
        }
    }
}
