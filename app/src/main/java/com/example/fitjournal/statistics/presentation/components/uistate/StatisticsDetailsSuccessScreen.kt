package com.example.fitjournal.statistics.presentation.components.uistate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.domain.model.WorkoutAnalytics
import com.example.fitjournal.statistics.presentation.components.badges.PersonalRecordSection
import com.example.fitjournal.statistics.presentation.components.graphs.title.CalisthenicsGraphTitle
import com.example.fitjournal.statistics.presentation.components.graphs.title.CardioGraphTitle
import com.example.fitjournal.statistics.presentation.components.graphs.title.WeightTrainingGraphTitle
import com.example.fitjournal.statistics.presentation.components.graphs.ui.GraphSectionForCalisthenics
import com.example.fitjournal.statistics.presentation.components.graphs.ui.GraphSectionForCardio
import com.example.fitjournal.statistics.presentation.components.graphs.ui.GraphSectionForWeightLifting
import com.example.fitjournal.statistics.presentation.components.tabs.StatisticsTabRow
import com.example.fitjournal.statistics.presentation.components.text.WorkoutNameTitle
import com.example.fitjournal.statistics.presentation.model.StatisticsDetailsEvents
import com.example.fitjournal.statistics.presentation.model.StatisticsDetailsUiModel
import com.example.fitjournal.statistics.presentation.util.getGraphData
import com.example.fitjournal.statistics.presentation.util.getPersonalRecordAnalytics

@Composable
fun StatisticsDetailsSuccessScreen(
    modifier: Modifier = Modifier,
    currentlyViewedWorkoutStats: WorkoutAnalytics?,
    statisticsDetailsUiState: StatisticsDetailsUiModel
) {
    val workout = statisticsDetailsUiState.realmList.first().workoutDetailsModel

    val timeRangeOfWorkouts by rememberSaveable(statisticsDetailsUiState.timeRangeEnum) {
        mutableStateOf(statisticsDetailsUiState.timeRangeEnum)
    }
    Column(modifier = modifier) {
        WorkoutNameTitle(workoutName = workout.name)
        StatisticsTabRow(
            timeRangeOfWorkouts = timeRangeOfWorkouts.ordinal,
            getStatsBasedOnTimeSelected = {
                statisticsDetailsUiState.handleStatisticsDetailsClickEvents(
                    StatisticsDetailsEvents.UpdateTimeRange(
                        it
                    )
                )
            }
        )
        when (workout.workoutTypeEnum) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                WeightTrainingGraphTitle(
                    graphDisplayedEnum = statisticsDetailsUiState.weightTrainingGraphs,
                    updateGraphDisplayed = { graphToShow ->
                        statisticsDetailsUiState.handleStatisticsDetailsClickEvents(
                            StatisticsDetailsEvents.UpdateWeightTrainingGraphShown(graphToShow)
                        )
                    }
                )
            }

            WorkoutTypeEnum.CALISTHENICS -> {
                CalisthenicsGraphTitle(
                    graphDisplayedEnum = statisticsDetailsUiState.calisthenicGraphs,
                    updateGraphDisplayed = { graphToShow ->
                        statisticsDetailsUiState.handleStatisticsDetailsClickEvents(
                            StatisticsDetailsEvents.UpdateCalisthenicsGraphShown(graphToShow)
                        )
                    }
                )
            }

            WorkoutTypeEnum.CARDIO -> {
                CardioGraphTitle(
                    graphDisplayedEnum = statisticsDetailsUiState.cardioGraphs,
                    updateGraphDisplayed = { graphToShow ->
                        statisticsDetailsUiState.handleStatisticsDetailsClickEvents(
                            StatisticsDetailsEvents.UpdateCardioGraphShown(graphToShow)
                        )
                    }
                )
            }
        }
        currentlyViewedWorkoutStats?.let { workoutStats ->
            val graphData = getGraphData(
                workoutStats = workoutStats,
                timeRangeOfWorkouts = timeRangeOfWorkouts
            )
            val prData = getPersonalRecordAnalytics(workoutStats)
            Column(modifier = Modifier.padding(start = Spacing.spacing8)) {
                when (graphData) {
                    is GraphData.Calisthenics -> GraphSectionForCalisthenics(
                        graphData = graphData,
                        calisthenicGraphs = statisticsDetailsUiState.calisthenicGraphs
                    )

                    is GraphData.Cardio -> {
                        GraphSectionForCardio(
                            graphData = graphData,
                            cardioGraphs = statisticsDetailsUiState.cardioGraphs
                        )
                    }

                    is GraphData.WeightTraining -> {
                        GraphSectionForWeightLifting(
                            graphData = graphData,
                            weightTrainingGraphs = statisticsDetailsUiState.weightTrainingGraphs
                        )
                    }
                }
            }
            PersonalRecordSection(
                timeRangeOfWorkouts = timeRangeOfWorkouts,
                personalRecordData = prData
            )
        }
    }
}
