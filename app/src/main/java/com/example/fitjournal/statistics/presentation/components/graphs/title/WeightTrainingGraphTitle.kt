package com.example.fitjournal.statistics.presentation.components.graphs.title

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.statistics.presentation.components.text.GraphTitle
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes

@Composable
fun WeightTrainingGraphTitle(
    graphDisplayedEnum: GraphUiTypes.WeightTrainingGraphs,
    updateGraphDisplayed: (GraphUiTypes.WeightTrainingGraphs) -> Unit
) {
    val graphDisplayed by rememberSaveable(graphDisplayedEnum) {
        mutableStateOf(graphDisplayedEnum)
    }

    when (graphDisplayed) {
        GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE -> {
            GraphTitle(
                text = stringResource(id = R.string.title_date_vs_weight_lifted),
                showBackArrow = false,
                showNextArrow = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing32,
                        vertical = Spacing.spacing8
                    ),
                onNextArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE)
                }
            )
        }

        GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE -> {
            GraphTitle(
                text = stringResource(id = R.string.title_date_vs_total_volume),
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
