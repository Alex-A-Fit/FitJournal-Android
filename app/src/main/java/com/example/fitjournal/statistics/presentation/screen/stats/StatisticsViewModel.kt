package com.example.fitjournal.statistics.presentation.screen.stats

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutItem
import com.example.fitjournal.statistics.domain.model.StatisticsWorkoutEvents
import com.example.fitjournal.statistics.domain.model.StatisticsWorkoutState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor() : ViewModel() {
    var statisticsWorkoutState: StatisticsWorkoutState by mutableStateOf(
        StatisticsWorkoutState(
            statisticsEvents = ::handleStatisticsWorkoutEvents
        )
    )
        private set

    init {
        // Dummy Data for now
        val workoutList = listOf("Bench", "Squats", "Lateral Raises", "Elevated Goblet Squats")
        val masterWorkoutList = workoutList.map { workout ->
            LibraryWorkoutItem(
                workoutName = workout,
                workoutDetails = "Details for $workout go here"
            )
        }
        setMasterListOfWorkouts(masterWorkoutList)
    }

    private fun handleStatisticsWorkoutEvents(event: StatisticsWorkoutEvents) {
        when (event) {
            is StatisticsWorkoutEvents.UpdateSearchBar -> {
                updateSearchBar(event.newSearchBarValue)
            }
        }
    }

    private fun updateSearchBar(newSearchBarValue: String) {
        updateStatisticsWorkoutState(
            statisticsWorkoutState.copy(searchBarValue = newSearchBarValue)
        )
    }

    private fun updateStatisticsWorkoutState(newStatisticsWorkoutState: StatisticsWorkoutState) {
        statisticsWorkoutState = newStatisticsWorkoutState
    }
}