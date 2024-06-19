package com.example.fitjournal.statistics.presentation.screen.statsdetails

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.screens.LoadingScreen
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.statistics.presentation.components.uistate.StatisticsDetailsSuccessScreen
import com.example.fitjournal.statistics.presentation.components.uistate.StatisticsErrorScreen
import com.example.fitjournal.statistics.presentation.model.StatisticsDetailsUiModel

@Composable
fun StatisticsDetailsScreen(
    modifier: Modifier,
    statisticsDetailsUiState: StatisticsDetailsUiModel
) {
    val rememberScrollState = rememberScrollState()
    when (val uiState = statisticsDetailsUiState.workoutStatisticsDetailsUiState) {
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
            StatisticsDetailsSuccessScreen(
                modifier = modifier.verticalScroll(rememberScrollState),
                currentlyViewedWorkoutStats = uiState.data,
                statisticsDetailsUiState = statisticsDetailsUiState
            )
        }
    }
}
