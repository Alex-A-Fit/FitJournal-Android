package com.alexafit.fitjournal.statistics.presentation.screen.stats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexafit.fitjournal.core.presentation.navigation.NavigationDirectionInterface
import com.alexafit.fitjournal.core.presentation.screens.LoadingScreen
import com.alexafit.fitjournal.core.util.localdate.formatToCommonDate
import com.alexafit.fitjournal.core.util.state.UiState
import com.alexafit.fitjournal.statistics.presentation.components.uistate.StatisticsErrorScreen
import com.alexafit.fitjournal.statistics.presentation.components.uistate.StatisticsNoneScreen
import com.alexafit.fitjournal.statistics.presentation.components.uistate.StatisticsSuccessScreen
import com.alexafit.fitjournal.statistics.presentation.model.StatisticsUiModel
import java.time.LocalDate

@Composable
fun StatisticsScreen(
    modifier: Modifier,
    statisticsUiState: StatisticsUiModel,
    navigateToDestination: (NavigationDirectionInterface) -> Unit
) {
    when (statisticsUiState.workoutStatisticsUiState) {
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
            StatisticsNoneScreen(
                modifier = Modifier.fillMaxSize(),
                navigateToAddWorkoutScreen = {
                    navigateToDestination(NavigationDirectionInterface.NavigateToAddWorkout(LocalDate.now().formatToCommonDate()))
                }
            )
        }

        is UiState.Success -> {
            StatisticsSuccessScreen(
                modifier = modifier,
                searchedTerm = statisticsUiState.searchedTerm,
                statisticsClickEvents = statisticsUiState.handleStatisticsClickEvents,
                listOfSearchedWorkouts = statisticsUiState.listOfSearchedWorkouts,
                navigateToStatisticsDetails = {
                    navigateToDestination(NavigationDirectionInterface.NavigateToStatisticsDetails(it))
                }
            )
        }
    }
}
