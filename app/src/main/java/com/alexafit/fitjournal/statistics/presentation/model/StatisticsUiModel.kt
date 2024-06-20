package com.alexafit.fitjournal.statistics.presentation.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alexafit.fitjournal.core.domain.model.WorkoutModel
import com.alexafit.fitjournal.core.util.state.UiState
import com.alexafit.fitjournal.library.presentation.model.WorkoutCategoryByJournal
import com.alexafit.fitjournal.statistics.domain.model.TimeRangeEnum

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
