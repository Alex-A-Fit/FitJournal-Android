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
import com.example.fitjournal.statistics.presentation.components.graphs.title.CalisthenicsGraphTitle
import com.example.fitjournal.statistics.presentation.components.graphs.title.CardioGraphTitle
import com.example.fitjournal.statistics.presentation.components.graphs.title.WeightTrainingGraphTitle
import com.example.fitjournal.statistics.presentation.components.graphs.ui.GraphSection
import com.example.fitjournal.statistics.presentation.components.tabs.StatisticsTabRow
import com.example.fitjournal.statistics.presentation.components.text.WorkoutNameTitle
import com.example.fitjournal.statistics.presentation.model.GraphUiTypes
import com.example.fitjournal.statistics.presentation.model.StatisticsDetailsEvents
import com.example.fitjournal.statistics.presentation.model.StatisticsDetailsUiModel
import com.example.fitjournal.statistics.presentation.util.getGraphData

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
                    timeRangeEnum = timeRangeOfWorkouts,
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
                    timeRangeEnum = timeRangeOfWorkouts,
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
            Column(modifier = Modifier.padding(start = Spacing.spacing8)) {
                GraphSection(
                    graphData = when (graphData) {
                        is GraphData.Calisthenics -> {
                            when (statisticsDetailsUiState.calisthenicGraphs) {
                                GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> graphData.totalRepsToDate
                                GraphUiTypes.CalisthenicsGraphs.TOTAL_TIME_OVER_DATE -> graphData.totalTimeToDate
                                GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> graphData.totalWeightUsedToDate
                            }
                        }

                        is GraphData.Cardio -> {
                            when (statisticsDetailsUiState.cardioGraphs) {
                                GraphUiTypes.CardioGraphs.DISTANCE_OVER_DATE -> graphData.totalDistanceToDate
                                GraphUiTypes.CardioGraphs.AVERAGE_SPEED_OVER_DATE -> graphData.averageSpeedToDate
                            }
                        }

                        is GraphData.WeightTraining -> {
                            when (statisticsDetailsUiState.weightTrainingGraphs) {
                                GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE -> graphData.topWeightToDate
                                GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE -> graphData.mostVolumeToDate
                            }
                        }
                    },
                    stringForGraphPopUp = { x, y ->
                        when (graphData) {
                            is GraphData.Calisthenics -> {
                                when (statisticsDetailsUiState.calisthenicGraphs) {
                                    GraphUiTypes.CalisthenicsGraphs.REPS_OVER_DATE -> "Total Reps: $y $x"
                                    GraphUiTypes.CalisthenicsGraphs.TOTAL_TIME_OVER_DATE -> "Total Time: $y $x"
                                    GraphUiTypes.CalisthenicsGraphs.TOTAL_WEIGHT_OVER_DATE -> "Weight Used: $y $x"
                                }
                            }

                            is GraphData.Cardio -> {
                                when (statisticsDetailsUiState.cardioGraphs) {
                                    GraphUiTypes.CardioGraphs.DISTANCE_OVER_DATE -> "Distance Traveled: $y $x"
                                    GraphUiTypes.CardioGraphs.AVERAGE_SPEED_OVER_DATE -> "Average Speed: $y $x"
                                }
                            }

                            is GraphData.WeightTraining -> {
                                when (statisticsDetailsUiState.weightTrainingGraphs) {
                                    GraphUiTypes.WeightTrainingGraphs.WEIGHT_OVER_DATE -> "Weight Lifted: $y $x"
                                    GraphUiTypes.WeightTrainingGraphs.VOLUME_OVER_DATE -> "Workout Volume: $y $x"
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
