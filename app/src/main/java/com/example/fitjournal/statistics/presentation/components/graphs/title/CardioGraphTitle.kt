package com.example.fitjournal.statistics.presentation.components.graphs.title

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.statistics.presentation.components.text.GraphTitle
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes

@Composable
fun CardioGraphTitle(
    graphDisplayedEnum: GraphUiTypes.CardioGraphs,
    updateGraphDisplayed: (GraphUiTypes.CardioGraphs) -> Unit
) {
    val graphDisplayed by rememberSaveable(graphDisplayedEnum) {
        mutableStateOf(graphDisplayedEnum)
    }

    when (graphDisplayed) {
        GraphUiTypes.CardioGraphs.DISTANCE_OVER_DATE -> {
            GraphTitle(
                text = "Date vs Total Distance",
                showBackArrow = false,
                showNextArrow = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing32,
                        vertical = Spacing.spacing8
                    ),
                onNextArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.CardioGraphs.AVERAGE_SPEED_OVER_DATE)
                }
            )
        }

        GraphUiTypes.CardioGraphs.AVERAGE_SPEED_OVER_DATE -> {
            GraphTitle(
                text = "Date vs Average Speed",
                showBackArrow = true,
                showNextArrow = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    ),
                onBackArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.CardioGraphs.DISTANCE_OVER_DATE)
                }
            )
        }
    }
}
