package com.example.fitjournal.statistics.presentation.screen.stats

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.screens.LoadingScreen
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.statistics.presentation.components.uistate.StatisticsErrorScreen
import com.example.fitjournal.statistics.presentation.components.uistate.StatisticsSuccessScreen
import com.example.fitjournal.statistics.presentation.model.StatisticsUiModel

@Composable
fun StatisticsScreen(
    modifier: Modifier,
    statisticsUiState: StatisticsUiModel,
    navigateToDestination: (NavigationInterface) -> Unit
) {
    when (val uiState = statisticsUiState.workoutStatisticsUiState) {
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
            LoadingScreen()
        }

        is UiState.Success -> {
            StatisticsSuccessScreen(
                modifier = modifier,
                searchedTerm = statisticsUiState.searchedTerm,
                statisticsClickEvents = statisticsUiState.handleStatisticsClickEvents,
                listOfSearchedWorkouts = statisticsUiState.listOfSearchedWorkouts,
                navigateToStatisticsDetails = {
                    navigateToDestination(NavigationInterface.NavigateToStatisticsDetails(it))
                }
            )
        }
    }
}
