package com.example.fitjournal.statistics.presentation.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategoryByJournal
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum

data class StatisticsUiModel(
    // realm db list
    val realmList: List<WorkoutModel> = emptyList(),
    // list for grouping realm into categories
    val masterWorkoutList: List<WorkoutCategoryByJournal> = emptyList(),
    val listOfSearchedWorkouts: SnapshotStateList<WorkoutCategoryByJournal> = mutableStateListOf(),
    val workoutStatisticsUiState: UiState<Unit> = UiState.None,

    // search bar
    val searchedTerm: String = "",
    // click events
    val handleStatisticsClickEvents: (StatisticsEvents) -> Unit,

    val timeRangeEnum: TimeRangeEnum = TimeRangeEnum.WEEK
)
