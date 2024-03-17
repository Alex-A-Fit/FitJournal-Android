package com.example.fitjournal.home.presentation.screen.home

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.home.data.mockdata.MockData
import com.example.fitjournal.home.domain.managers.DateManager
import com.example.fitjournal.home.domain.model.WorkoutModel
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import com.example.fitjournal.home.presentation.model.enum.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.state.HomeScreenUiState
import com.example.fitjournal.home.presentation.model.ui.CardUiModel
import com.example.fitjournal.home.presentation.model.ui.FilterWorkoutUiModel
import com.example.fitjournal.home.presentation.model.ui.WorkoutUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    var homeScreenState: HomeScreenUiState by mutableStateOf(HomeScreenUiState())
        private set

    init {
        val todayDate = DateManager.formatDate(homeScreenState.currentDate)
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDate = todayDate,
                listOfWorkouts = MockData.ListOfWorkouts,
                listOfVisibleWorkouts = determineWorkout(MockData.ListOfWorkouts)
            )
        )
    }

    fun getNextDate() {
        val nextDay = DateManager.getNextDate(homeScreenState.currentDateTime)
        val nextDayInMilliseconds = DateManager.getTimeInMilliseconds(nextDay.localDateTime)
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDate = nextDay.localDateString,
                currentDateTime = nextDay.localDateTime,
                currentDateInMillis = nextDayInMilliseconds
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
                currentDateInMillis = previousDayInMilliseconds
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
                currentDateInMillis = dateInMillis
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

    private fun determineWorkout(listOfWorkouts: List<WorkoutModel>): List<WorkoutUiModel> {
        return listOfWorkouts.map {
            when (it.workoutTypeEnum) {
                WorkoutTypeEnum.WEIGHT_TRAINING -> {
                    val topSet = it.weightLiftingModel?.maxBy { set ->
                        set.weight
                    }
                    WorkoutUiModel(
                        workoutType = WorkoutTypeEnum.WEIGHT_TRAINING,
                        exerciseCardModel = CardUiModel(
                            name = it.name,
                            icon = it.icon,
                            reps = topSet?.reps,
                            weight = topSet?.weight
                        )
                    )
                }

                WorkoutTypeEnum.CALISTHENICS -> {
                    val mostRecentSession = it.calisthenicsModel?.last()
                    WorkoutUiModel(
                        workoutType = WorkoutTypeEnum.CALISTHENICS,
                        exerciseCardModel = CardUiModel(
                            name = it.name,
                            icon = it.icon,
                            reps = mostRecentSession?.reps,
                            time = mostRecentSession?.time
                        )
                    )
                }

                WorkoutTypeEnum.CARDIO -> {
                    val mostRecentSession = it.cardioModel?.last()
                    WorkoutUiModel(
                        workoutType = WorkoutTypeEnum.CARDIO,
                        exerciseCardModel = CardUiModel(
                            name = it.name,
                            icon = it.icon,
                            time = mostRecentSession?.time,
                            laps = mostRecentSession?.laps,
                            distance = mostRecentSession?.distance,
                            distanceType = mostRecentSession?.distanceType ?: CardioDistanceType.MILES
                        )
                    )
                }
            }
        }
    }
}
