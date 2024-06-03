package com.example.fitjournal.statistics.presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.util.filter.searchForJournalEntry
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.library.presentation.screen.library.utils.mapToStatisticsUiList
import com.example.fitjournal.statistics.domain.usecase.CreateWorkoutAnalyticsUseCase
import com.example.fitjournal.statistics.presentation.model.StatisticsEvents
import com.example.fitjournal.statistics.presentation.model.StatisticsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase,
    private val createWorkoutAnalyticsUseCase: CreateWorkoutAnalyticsUseCase
) : ViewModel() {
    var statisticsUiState: StatisticsUiModel by mutableStateOf(
        StatisticsUiModel(
            handleStatisticsClickEvents = ::statisticsClickEvents
        )
    )
        private set

    private fun statisticsClickEvents(event: StatisticsEvents) {
        when (event) {
            is StatisticsEvents.FilterSearchByWorkout -> {
                val filteredList = searchForJournalEntry(
                    event.workout,
                    statisticsUiState.masterWorkoutList
                )
                updateStatisticsState(
                    newStatisticsState = statisticsUiState.copy(
                        listOfSearchedWorkouts = filteredList.toMutableStateList(),
                        searchedTerm = event.workout
                    )
                )
            }

            StatisticsEvents.ClearSearchBarFilter -> {
                updateStatisticsState(
                    newStatisticsState = statisticsUiState.copy(
                        listOfSearchedWorkouts = statisticsUiState.masterWorkoutList.toMutableStateList(),
                        searchedTerm = ""
                    )
                )
            }

            is StatisticsEvents.GetWorkoutStats -> {
                val workoutStatistics = statisticsUiState.realmList.filter { it.workoutDetailsModel.name == event.workoutName }
                val workoutAnalytics = createWorkoutAnalyticsUseCase(workoutStatistics)
                updateStatisticsState(
                    newStatisticsState = statisticsUiState.copy(
                        workoutStatistics = if (workoutStatistics.isEmpty()) {
                            UiState.Empty
                        } else {
                            UiState.Success(workoutStatistics)
                        },
                        workoutAnalytics = workoutAnalytics
                    )
                )
            }

            is StatisticsEvents.UpdateTimeRange -> {
                updateStatisticsState(
                    newStatisticsState = statisticsUiState.copy(
                        timeRangeEnum = event.timeRangeEnum
                    )
                )
            }
        }
    }

    fun getDataFromRealmDb() {
        viewModelScope.launch {
            val workoutList = realmWorkoutEntryUseCase.getRealmWorkoutEntryList()
            if (workoutList.isNotEmpty()) {
                val libraryList = workoutList.groupBy { it.workoutDetailsModel.name.first().toString() }.toSortedMap()
                val masterWorkoutList = mapToStatisticsUiList(libraryList)
                updateStatisticsState(
                    newStatisticsState = statisticsUiState.copy(
                        realmList = workoutList,
                        masterWorkoutList = masterWorkoutList,
                        listOfSearchedWorkouts = if (statisticsUiState.searchedTerm.isEmpty()) {
                            masterWorkoutList.toMutableStateList()
                        } else {
                            searchForJournalEntry(
                                statisticsUiState.searchedTerm,
                                statisticsUiState.masterWorkoutList
                            ).toMutableStateList()
                        }
                    )
                )
            }
        }
    }

    private fun updateStatisticsState(newStatisticsState: StatisticsUiModel) {
        statisticsUiState = newStatisticsState
    }
}
