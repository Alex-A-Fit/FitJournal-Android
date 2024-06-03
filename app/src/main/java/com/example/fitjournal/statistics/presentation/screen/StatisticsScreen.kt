package com.example.fitjournal.statistics.presentation.screen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.screens.LoadingScreen
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.statistics.presentation.components.uistate.StatisticsErrorScreen
import com.example.fitjournal.statistics.presentation.components.uistate.StatisticsNoneState
import com.example.fitjournal.statistics.presentation.components.uistate.StatisticsSuccessScreen
import com.example.fitjournal.statistics.presentation.model.StatisticsUiModel

@Composable
fun StatisticsScreen(
    modifier: Modifier,
    statisticsUiState: StatisticsUiModel,
    navigateToDestination: (NavigationInterface) -> Unit
) {
    val searchText = rememberSaveable(statisticsUiState.searchedTerm) {
        mutableStateOf(statisticsUiState.searchedTerm)
    }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    when (val uiState = statisticsUiState.workoutStatistics) {
        UiState.Empty -> {
            StatisticsErrorScreen()
        }

        UiState.Error -> {
            StatisticsErrorScreen()
        }

        UiState.Loading -> {
            LoadingScreen()
        }

        UiState.None -> {
            StatisticsNoneState(
                modifier = modifier,
                searchedTerm = statisticsUiState.searchedTerm,
                statisticsClickEvents = statisticsUiState.handleStatisticsClickEvents,
                listOfSearchedWorkouts = statisticsUiState.listOfSearchedWorkouts
            )
        }

        is UiState.Success -> {
            val workout = uiState.data.first().workoutDetailsModel
            StatisticsSuccessScreen(
                modifier = modifier,
                workoutName = workout.name,
                timeRangeEnum = statisticsUiState.timeRangeEnum,
                currentlyViewedWorkoutStats = statisticsUiState.workoutAnalytics,
                statisticsClickEvents = statisticsUiState.handleStatisticsClickEvents,
                workoutTypeEnum = workout.workoutTypeEnum
            )
        }
    }
}

//    val steps = 5
//    val pointsData: List<Point> =
//        listOf(Point(0f, 900f), Point(1f, 900f), Point(2f, 200f), Point(3f, 60f), Point(4f, 10f))
//    val xAxisData = AxisData.Builder()
//        //the distance between each x axis point
//        .axisStepSize(75.dp)
//        .backgroundColor(Color.Transparent)
//        .steps(pointsData.size - 1)
//        .labelData { i -> i.toString() }
//        //padding between the label and the x axis line
//        .labelAndAxisLinePadding(15.dp)
//        .axisLineColor(MaterialTheme.colorScheme.onPrimary)
//        .axisLabelColor(MaterialTheme.colorScheme.primary)
//        .axisLabelFontSize(16.sp)
//        .build()
//
//    val yAxisData = AxisData.Builder()
//        .steps(steps)
//        .backgroundColor(Color.Transparent)
//        .labelAndAxisLinePadding(20.dp)
//        .labelData { i ->
//            val yScale = 900 / steps
//            (i * yScale).toString()
//        }
//        .axisLineColor(MaterialTheme.colorScheme.onPrimary)
//        .axisLabelColor(MaterialTheme.colorScheme.primary).build()
//    val lineChartData = LineChartData(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                Line(
//                    dataPoints = pointsData,
//                    LineStyle(
//                        color = MaterialTheme.colorScheme.primary,
//                        lineType = LineType.SmoothCurve(isDotted = false)
//                    ),
//                    IntersectionPoint(
//                        color = MaterialTheme.colorScheme.primary
//                    ),
//                    SelectionHighlightPoint(
//                        color = MaterialTheme.colorScheme.primary
//                    ),
//                    ShadowUnderLine(
//                        alpha = 0.5f,
//                        brush = Brush.verticalGradient(
//                            colors = listOf(
//                                MaterialTheme.colorScheme.primary,
//                                Color.Transparent
//                            )
//                        )
//                    ),
//                    SelectionHighlightPopUp()
//                )
//            ),
//        ),
//        xAxisData = xAxisData,
//        yAxisData = yAxisData,
//        gridLines = GridLines(
//            color = MaterialTheme.colorScheme.secondary
//        ),
//        backgroundColor = MaterialTheme.colorScheme.background
//    )
//
//        Spacer(modifier = Modifier.height(Spacing.spacing16))
//        LineChart(
//            modifier = Modifier
//                .fillMaxWidth()
//                .heightIn(min = 300.dp, max = 500.dp)
//                .padding(horizontal = Spacing.spacing8),
//            lineChartData = lineChartData
//        )
//        Spacer(modifier = Modifier.height(Spacing.spacing16))
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = Spacing.spacing8)
//                    .background(color = MaterialTheme.colorScheme.secondary, RoundedCornerShape(Spacing.spacing16))
//                    .weight(1f)
//                    .heightIn(min = 100.dp, max = 200.dp)
//            ) {
//                Column(modifier = Modifier.padding(Spacing.spacing16)) {
//                    Text(text = "Best Record in the past Month")
//                    Spacer(modifier = Modifier.height(Spacing.spacing12))
//                    Text(
//                        text = "300lbs",
//                        style = MaterialTheme.typography.headlineSmall
//                    )
//                }
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = Spacing.spacing8)
//                    .background(color = SuccessGreen, RoundedCornerShape(Spacing.spacing16))
//                    .weight(1f)
//                    .heightIn(min = 100.dp, max = 200.dp)
//            ) {
//                Column(modifier = Modifier.padding(Spacing.spacing16)) {
//                    Text(text = "Current Personal Record")
//                    Spacer(modifier = Modifier.height(Spacing.spacing12))
//                    Text(
//                        text = "400lbs",
//                        style = MaterialTheme.typography.headlineSmall
//                    )
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(Spacing.spacing96))
//    }
// }
