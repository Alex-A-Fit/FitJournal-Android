package com.alexafit.fitjournal.statistics.presentation.components.graphs.title

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.statistics.presentation.components.text.GraphTitle
import com.alexafit.fitjournal.statistics.presentation.model.GraphUiTypes

@Composable
fun CalisthenicsGraphTitle(
    graphDisplayedEnum: GraphUiTypes.CalisthenicsGraphs,
    updateGraphDisplayed: (GraphUiTypes.CalisthenicsGraphs) -> Unit

) {
    val graphDisplayed by rememberSaveable(graphDisplayedEnum) {
        mutableStateOf(graphDisplayedEnum)
    }

    when (graphDisplayed) {
        GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> {
            GraphTitle(
                text = stringResource(id = R.string.title_date_vs_total_reps),
                showBackArrow = false,
                showNextArrow = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing32,
                        vertical = Spacing.spacing8
                    ),
                onNextArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE)
                }
            )
        }

        GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> {
            GraphTitle(
                text = stringResource(id = R.string.title_date_vs_total_weight),
                showBackArrow = true,
                showNextArrow = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    ),
                onBackArrowClicked = {
                    updateGraphDisplayed(GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE)
                }
            )
        }
    }
}
