package com.example.fitjournal.home.presentation.screen.home

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.domain.mapper.mapToWorkoutUiModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.presentation.model.WorkoutUiModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.model.state.HomeScreenUiState
import com.example.fitjournal.home.presentation.model.ui.FilterWorkoutUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase
) : ViewModel() {
    var homeScreenState: HomeScreenUiState by mutableStateOf(HomeScreenUiState())
        private set

    fun getNextDate() {
        val nextDay = DateManager.getNextDate(homeScreenState.currentDateTime)
        val nextDayInMilliseconds = DateManager.getTimeInMilliseconds(nextDay.localDateTime)
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDate = nextDay.localDateString,
                currentDateTime = nextDay.localDateTime,
                currentDateInMillis = nextDayInMilliseconds,
                listOfVisibleWorkouts = createWorkoutUiModel(
                    listOfWorkouts = homeScreenState.listOfWorkouts ?: emptyList(),
                    dateToFilterBy = nextDay.localDateString
                )
            )
        )
    }

    fun getPreviousDate() {
        val previousDay = DateManager.getPreviousDate(homeScreenState.currentDateTime)
        val previousDayInMilliseconds = DateManager.getTimeInMilliseconds(previousDay.localDateTime)
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDate = previousDay.localDateString,
                currentDateTime = previousDay.localDateTime,
                currentDateInMillis = previousDayInMilliseconds,
                listOfVisibleWorkouts = createWorkoutUiModel(
                    listOfWorkouts = homeScreenState.listOfWorkouts ?: emptyList(),
                    dateToFilterBy = previousDay.localDateString
                )
            )
        )
    }

    fun showSnackBar(snackBarHostState: SnackbarHostState) {
        viewModelScope.launch {
            snackBarHostState.showSnackbar("Date Updated", duration = SnackbarDuration.Short)
        }
    }

    fun getSelectedDate(dateInMillis: Long) {
        val selectedDate = DateManager.getSelectedDate(
            dateInMillis
        )
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDateTime = selectedDate.localDateTime,
                currentDate = selectedDate.localDateString,
                currentDateInMillis = dateInMillis,
                listOfVisibleWorkouts = createWorkoutUiModel(
                    listOfWorkouts = homeScreenState.listOfWorkouts ?: emptyList(),
                    dateToFilterBy = selectedDate.localDateString
                )
            )
        )
    }

    fun updateDatePickerDialog(isDatePickerShowing: Boolean) {
        updateHomeScreenState(newHomeScreenState = homeScreenState.copy(isDatePickerDialogShowing = isDatePickerShowing))
    }

    fun updateFilterDialog(isFilterDialogShowing: Boolean) {
        updateHomeScreenState(newHomeScreenState = homeScreenState.copy(isFilterDialogShowing = isFilterDialogShowing))
    }

    private fun updateHomeScreenState(newHomeScreenState: HomeScreenUiState) {
        homeScreenState = newHomeScreenState
    }

    fun filterWorkouts(filteredWorkoutList: List<WorkoutTypeEnum>) {
        val currentFilterList = homeScreenState.filterDialogList
        // Adjust the filter list with the passed in parameter values
        val newFilteredList = currentFilterList.map {
            FilterWorkoutUiModel(
                isWorkoutSelected = filteredWorkoutList.contains(it.exerciseType),
                exerciseType = it.exerciseType
            )
        }
        updateHomeScreenState(newHomeScreenState = homeScreenState.copy(filterDialogList = newFilteredList))
        // TODO("WE would want to now filter out the visible cards ")
        Log.d(
            "filter",
            "Filter works. List of cards should now show exercises based on filter enum types: $filteredWorkoutList"
        )
    }

    private fun createWorkoutUiModel(
        listOfWorkouts: List<WorkoutModel>,
        dateToFilterBy: String
    ): UiState<List<WorkoutUiModel>> {
        if (listOfWorkouts.isEmpty()) return UiState.Empty
        val workoutsFilteredByDate = filterWorkoutByDate(
            listOfWorkouts,
            dateToFilterBy
        )
        val workoutsMapped = workoutsFilteredByDate.map {
            it.mapToWorkoutUiModel()
        }
        return UiState.Success(workoutsMapped)
    }

    private fun filterWorkoutByDate(
        workoutList: List<WorkoutModel>,
        dateToFilterBy: String
    ): List<WorkoutModel> {
        return workoutList.filter {
            it.date == dateToFilterBy
        }
    }

    fun getDataFromRealmDb() {
        viewModelScope.launch {
            val workoutList = realmWorkoutEntryUseCase.getRealmWorkoutEntryList()
            if (workoutList.isNotEmpty()) {
                updateHomeScreenState(
                    newHomeScreenState = homeScreenState.copy(
                        listOfWorkouts = workoutList,
                        listOfVisibleWorkouts = createWorkoutUiModel(
                            listOfWorkouts = workoutList,
                            dateToFilterBy = homeScreenState.currentDate
                        )
                    )
                )
            }
        }
    }

    fun clearUiState() {
        updateHomeScreenState(
            newHomeScreenState =
            homeScreenState.copy(
                listOfVisibleWorkouts = UiState.None
            )
        )
    }
}
