package com.alexafit.fitjournal.home.presentation.model.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alexafit.fitjournal.core.domain.model.WorkoutModel
import com.alexafit.fitjournal.core.presentation.model.WorkoutUiModel
import com.alexafit.fitjournal.core.util.state.UiState
import com.alexafit.fitjournal.home.presentation.model.events.HomeAppBarEvents
import com.alexafit.fitjournal.home.presentation.model.events.HomeScreenEvents
import com.alexafit.fitjournal.home.presentation.model.ui.FilterWorkoutUiModel
import com.alexafit.fitjournal.home.presentation.util.constants.GeneralConstants
import com.alexafit.fitjournal.home.presentation.util.filter.HomeScreenFilter
import java.time.LocalDateTime

data class HomeScreenUiState(
    val currentDateTime: LocalDateTime = GeneralConstants.todayDateTime,
    val currentDate: String = GeneralConstants.todayDate,
    val currentDateInMillis: Long = GeneralConstants.todayDateTimeInMilli,
    val isDatePickerDialogShowing: Boolean = false,
    val isHelpDialogShowing: Boolean = false,
    val filterList: SnapshotStateList<FilterWorkoutUiModel> = HomeScreenFilter.filterList,
    // all workouts ever inputted
    val masterListOfWorkouts: List<WorkoutModel>? = null,
    // current ui visible workouts
    val listOfVisibleWorkoutsUiState: UiState<List<WorkoutUiModel>> = UiState.None,
    // all workouts for current date
    val currentDateListOfWorkouts: List<WorkoutModel> = emptyList(),
    val homeScreenEvents: (HomeScreenEvents) -> Unit,
    val homeAppBarEvents: (HomeAppBarEvents) -> Unit
)
