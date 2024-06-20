package com.alexafit.fitjournal.statistics.presentation.screen.statsdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexafit.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.alexafit.fitjournal.core.util.state.UiState
import com.alexafit.fitjournal.statistics.domain.usecase.CreateWorkoutAnalyticsUseCase
import com.alexafit.fitjournal.statistics.presentation.components.uistate.WorkoutName
import com.alexafit.fitjournal.statistics.presentation.model.StatisticsDetailsEvents
import com.alexafit.fitjournal.statistics.presentation.model.StatisticsDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsDetailsViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase,
    private val createWorkoutAnalyticsUseCase: CreateWorkoutAnalyticsUseCase
) : ViewModel() {
    var statisticsDetailsUiState: StatisticsDetailsUiModel by mutableStateOf(
        StatisticsDetailsUiModel(
            handleStatisticsDetailsClickEvents = ::statisticsDetailsClickEvents
        )
    )
        private set

    private fun statisticsDetailsClickEvents(event: StatisticsDetailsEvents) {
        when (event) {
            is StatisticsDetailsEvents.UpdateTimeRange -> {
                updateStatisticsDetailsState(
                    newStatisticsDetailsState = statisticsDetailsUiState.copy(
                        timeRangeEnum = event.timeRangeEnum
                    )
                )
            }

            is StatisticsDetailsEvents.UpdateCalisthenicsGraphShown -> {
                updateStatisticsDetailsState(
                    newStatisticsDetailsState = statisticsDetailsUiState.copy(
                        calisthenicGraphs = event.graphToShow
                    )
                )
            }
            is StatisticsDetailsEvents.UpdateCardioGraphShown -> {
                updateStatisticsDetailsState(
                    newStatisticsDetailsState = statisticsDetailsUiState.copy(
                        cardioGraphs = event.graphToShow
                    )
                )
            }
            is StatisticsDetailsEvents.UpdateWeightTrainingGraphShown -> {
                updateStatisticsDetailsState(
                    newStatisticsDetailsState = statisticsDetailsUiState.copy(
                        weightTrainingGraphs = event.graphToShow
                    )
                )
            }
        }
    }

    fun getDataFromRealmDb(workoutName: WorkoutName) {
        if (workoutName.isEmpty()) {
            updateStatisticsDetailsState(
                newStatisticsDetailsState = statisticsDetailsUiState.copy(
                    workoutStatisticsDetailsUiState = UiState.Error
                )
            )
            return
        }
        viewModelScope.launch {
            val workoutList = realmWorkoutEntryUseCase.getRealmWorkoutEntryListWithName(workoutName)
            if (workoutList.isNotEmpty()) {
                val workoutAnalytics = createWorkoutAnalyticsUseCase(workoutList)
                updateStatisticsDetailsState(
                    newStatisticsDetailsState = statisticsDetailsUiState.copy(
                        realmList = workoutList,
                        workoutStatisticsDetailsUiState = if (workoutList.isEmpty()) {
                            UiState.Empty
                        } else {
                            UiState.Success(workoutAnalytics)
                        }
                    )
                )
            } else {
                updateStatisticsDetailsState(
                    newStatisticsDetailsState = statisticsDetailsUiState.copy(
                        realmList = emptyList(),
                        workoutStatisticsDetailsUiState = UiState.Empty
                    )
                )
            }
        }
    }

    private fun updateStatisticsDetailsState(newStatisticsDetailsState: StatisticsDetailsUiModel) {
        statisticsDetailsUiState = newStatisticsDetailsState
    }
}
