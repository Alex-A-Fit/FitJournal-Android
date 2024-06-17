package com.example.fitjournal.onboarding.presentation.component.onboardingsections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.yml.charts.common.model.Point
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.util.localdate.formatToCommonDate
import com.example.fitjournal.onboarding.presentation.component.TypewriterText
import com.example.fitjournal.onboarding.presentation.component.carouselcircles.CarouselCircles
import com.example.fitjournal.onboarding.presentation.model.OnboardingSections
import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.domain.model.GraphValues
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum
import com.example.fitjournal.statistics.presentation.components.graphs.title.WeightTrainingGraphTitle
import com.example.fitjournal.statistics.presentation.components.graphs.ui.GraphSectionForWeightLifting
import com.example.fitjournal.statistics.presentation.components.tabs.StatisticsTabRow
import com.example.fitjournal.statistics.presentation.components.text.WorkoutNameTitle
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes
import java.time.LocalDate

@Composable
fun StatisticsSection(
    modifier: Modifier = Modifier,
    navigateToEndOfTutorial: () -> Unit
) {
    var showButton by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WorkoutNameTitle(workoutName = stringResource(id = R.string.text_squats))
        StatisticsTabRow(
            timeRangeOfWorkouts = TimeRangeEnum.WEEK.ordinal,
            getStatsBasedOnTimeSelected = {}
        )
        WeightTrainingGraphTitle(
            graphDisplayedEnum = GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE,
            updateGraphDisplayed = {}
        )
        GraphSectionForWeightLifting(
            graphData = GraphData.WeightTraining(
                listOf(
                    GraphValues(
                        point = Point(0f, 0f),
                        date = ""
                    ),
                    GraphValues(
                        point = Point(1f, 100f),
                        date = LocalDate.now().formatToCommonDate()
                    )
                ),
                emptyList()
            ),
            weightTrainingGraphs = GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    min = Spacing.spacing300,
                    max = Spacing.spacing400
                )
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        TypewriterText(
            text = stringResource(id = R.string.text_onboarding_typewriter_text_statistics),
            onTextEffectComplete = { showButton = true })
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        if (showButton) {
            SaveButton(
                text = stringResource(id = R.string.button_proceed_to_outro),
                textModifier = Modifier.padding(
                    horizontal = Spacing.spacing32,
                    vertical = Spacing.spacing4
                ),
                textStyle = MaterialTheme.typography.headlineMedium
            ) {
                navigateToEndOfTutorial()
            }
        }
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            CarouselCircles(
                currentOnboardingSection = OnboardingSections.StatsSection,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing64))
        }
    }
}
