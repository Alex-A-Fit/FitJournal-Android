package com.example.fitjournal.home.presentation.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.domain.mapper.mapToWorkoutUiModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.presentation.model.WorkoutUiModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.model.events.HomeAppBarEvents
import com.example.fitjournal.home.presentation.model.events.HomeScreenEvents
import com.example.fitjournal.home.presentation.model.state.HomeScreenUiState
import com.example.fitjournal.home.presentation.model.ui.FilterWorkoutUiModel
import com.example.fitjournal.home.presentation.util.filter.HomeScreenFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase
) : ViewModel() {
    var homeScreenState: HomeScreenUiState by mutableStateOf(
        HomeScreenUiState(
            homeScreenEvents = ::homeScreenEvents,
            homeAppBarEvents = ::homeAppBarEvents
        )
    )
        private set

    private fun homeScreenEvents(events: HomeScreenEvents) {
        when (events) {
            is HomeScreenEvents.SelectDateFromDatePicker -> {
                getSelectedDate(events.userSelectedDate)
            }

            is HomeScreenEvents.UpdateFilterDialog -> updateFilterDialog(
                isFilterDialogShowing = events.isDialogShowing
            )

            is HomeScreenEvents.OnConfirmFilterExercisesDialog -> filterWorkouts(
                events.filterList
            )

            HomeScreenEvents.DismissFilterExercisesDialog -> updateFilterDialog(
                isFilterDialogShowing = false
            )

            HomeScreenEvents.DismissDatePicker -> updateDatePickerDialog(
                isDatePickerShowing = false
            )

            HomeScreenEvents.ClearFilterExercisesDialog -> clearFilter()
            HomeScreenEvents.CollectRealmWorkoutEntryFromDb -> getDataFromRealmDb()
            HomeScreenEvents.SyncRealmWorkoutEntryFromDb -> {
                val shouldSyncOccur = realmWorkoutEntryUseCase.SyncDbWithViewModelUseCase()
                if (shouldSyncOccur) {
                    getDataFromRealmDb()
                }
            }
        }
    }

    private fun homeAppBarEvents(events: HomeAppBarEvents) {
        when (events) {
            HomeAppBarEvents.GetNextDate -> getNextDate()
            HomeAppBarEvents.GetPreviousDate -> getPreviousDate()
            is HomeAppBarEvents.ShowDatePickerDialog -> updateDatePickerDialog(
                isDatePickerShowing = events.showDialog
            )

            is HomeAppBarEvents.ShowFilterDialog -> updateFilterDialog(
                isFilterDialogShowing = events.showDialog
            )
        }
    }

    private fun getNextDate() {
        val nextDay = DateManager.getNextDate(homeScreenState.currentDateTime)
        val nextDayInMilliseconds = DateManager.getTimeInMilliseconds(nextDay.localDateTime)

        val filterListByDate = HomeScreenFilter.filterWorkoutByDate(
            workoutList = homeScreenState.masterListOfWorkouts ?: emptyList(),
            dateToFilterBy = nextDay.localDateString
        )
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDate = nextDay.localDateString,
                currentDateTime = nextDay.localDateTime,
                currentDateInMillis = nextDayInMilliseconds,
                listOfVisibleWorkoutsUiState = createWorkoutUiModel(
                    workoutList = filterListByDate,
                    filterList = homeScreenState.filterList
                ),
                currentDateListOfWorkouts = filterListByDate
            )
        )
    }

    private fun getPreviousDate() {
        val previousDay = DateManager.getPreviousDate(homeScreenState.currentDateTime)
        val previousDayInMilliseconds = DateManager.getTimeInMilliseconds(previousDay.localDateTime)

        val filterListByDate = HomeScreenFilter.filterWorkoutByDate(
            workoutList = homeScreenState.masterListOfWorkouts ?: emptyList(),
            dateToFilterBy = previousDay.localDateString
        )
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDate = previousDay.localDateString,
                currentDateTime = previousDay.localDateTime,
                currentDateInMillis = previousDayInMilliseconds,
                listOfVisibleWorkoutsUiState = createWorkoutUiModel(
                    workoutList = filterListByDate,
                    filterList = homeScreenState.filterList
                ),
                currentDateListOfWorkouts = filterListByDate
            )
        )
    }

    private fun getSelectedDate(dateInMillis: Long) {
        val selectedDate = DateManager.getSelectedDate(
            dateInMillis
        )
        val filterListByDate = HomeScreenFilter.filterWorkoutByDate(
            workoutList = homeScreenState.masterListOfWorkouts ?: emptyList(),
            dateToFilterBy = selectedDate.localDateString
        )
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDateTime = selectedDate.localDateTime,
                currentDate = selectedDate.localDateString,
                currentDateInMillis = dateInMillis,
                listOfVisibleWorkoutsUiState = createWorkoutUiModel(
                    workoutList = filterListByDate,
                    filterList = homeScreenState.filterList
                ),
                currentDateListOfWorkouts = filterListByDate,
                isDatePickerDialogShowing = false
            )
        )
    }

    private fun updateDatePickerDialog(isDatePickerShowing: Boolean) {
        updateHomeScreenState(newHomeScreenState = homeScreenState.copy(isDatePickerDialogShowing = isDatePickerShowing))
    }

    private fun updateFilterDialog(isFilterDialogShowing: Boolean) {
        updateHomeScreenState(newHomeScreenState = homeScreenState.copy(isFilterDialogShowing = isFilterDialogShowing))
    }

    private fun updateHomeScreenState(newHomeScreenState: HomeScreenUiState) {
        homeScreenState = newHomeScreenState
    }

    private fun clearFilter() {
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                filterList = HomeScreenFilter.filterList
            )
        )
    }

    private fun filterWorkouts(
        filteredWorkoutList: List<WorkoutTypeEnum>
    ) {
        val currentFilterList = homeScreenState.filterList
        // Adjust the filter list with the passed in parameter values
        val newFilteredList = currentFilterList.map {
            FilterWorkoutUiModel(
                isWorkoutFilterSelected = filteredWorkoutList.contains(it.exerciseType),
                exerciseType = it.exerciseType
            )
        }.toMutableStateList()
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                filterList = newFilteredList,
                listOfVisibleWorkoutsUiState = createWorkoutUiModel(
                    workoutList = homeScreenState.currentDateListOfWorkouts,
                    filterList = newFilteredList
                )
            )
        )
    }

    private fun createWorkoutUiModel(
        workoutList: List<WorkoutModel>,
        filterList: List<FilterWorkoutUiModel>
    ): UiState<List<WorkoutUiModel>> {
        if (workoutList.isEmpty()) return UiState.Empty
        val filteredWorkoutListByWorkoutType = HomeScreenFilter.filterWorkoutByEnumType(
            workoutList = workoutList,
            filterList = filterList
        )
        if (filteredWorkoutListByWorkoutType.isEmpty()) return UiState.Empty
        val workoutsMapped = filteredWorkoutListByWorkoutType.map {
            it.mapToWorkoutUiModel()
        }
        return UiState.Success(workoutsMapped)
    }

    // should be call on load or when needed for loading screen
    private fun getDataFromRealmDb() {
        updateHomeScreenState(newHomeScreenState = homeScreenState.copy(
            listOfVisibleWorkoutsUiState = UiState.Loading
        ))
        viewModelScope.launch {
            val masterWorkoutList = realmWorkoutEntryUseCase.getRealmWorkoutEntryList()
            if (masterWorkoutList.isNotEmpty()) {
                val workoutListForCurrentDay = HomeScreenFilter.filterWorkoutByDate(
                    workoutList = masterWorkoutList,
                    dateToFilterBy = homeScreenState.currentDate
                )
                updateHomeScreenState(
                    newHomeScreenState = homeScreenState.copy(
                        masterListOfWorkouts = masterWorkoutList,
                        listOfVisibleWorkoutsUiState = createWorkoutUiModel(
                            workoutList = workoutListForCurrentDay,
                            filterList = homeScreenState.filterList
                        ),
                        currentDateListOfWorkouts = workoutListForCurrentDay
                    )
                )
            } else {
                updateHomeScreenState(
                    newHomeScreenState = homeScreenState.copy(
                        masterListOfWorkouts = emptyList(),
                        listOfVisibleWorkoutsUiState = UiState.Empty,
                        currentDateListOfWorkouts = emptyList()
                    )
                )
            }
        }
    }
}
