package com.example.fitjournal.home.presentation.screen.home

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.FitJournal
import com.example.fitjournal.R
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.domain.usecase.realm.RealmUseCase
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import com.example.fitjournal.home.presentation.model.state.HomeScreenUiState
import com.example.fitjournal.home.presentation.model.ui.CardUiModel
import com.example.fitjournal.home.presentation.model.ui.FilterWorkoutUiModel
import com.example.fitjournal.home.presentation.model.ui.WorkoutUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val realmUseCase: RealmUseCase
) : ViewModel() {
    var homeScreenState: HomeScreenUiState by mutableStateOf(HomeScreenUiState())
        private set
    private val realm = FitJournal.realm

    // Query call to db that is set up as a flow
    // and will check for updates when it is still subscribed to the flow
    private val workoutsFlow = realm
        .query<RealmWorkoutEntry>()
        .asFlow()
        .map { results ->
            results.list.toList()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

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

    private fun determineWorkout(listOfWorkouts: List<WorkoutModel>): UiState<List<WorkoutUiModel>> {
        if (listOfWorkouts.isEmpty()) return UiState.Empty
        val workoutsMapped = listOfWorkouts.map {
            when (it.workoutTypeEnum) {
                WorkoutTypeEnum.WEIGHT_TRAINING -> {
                    val topSet = when (val workoutSets = it.workoutPropertiesModel) {
                        is WorkoutPropertiesModel.WeightLiftingProps -> {
                            workoutSets.props.maxBy { liftingModel ->
                                liftingModel.weight
                            }
                        }

                        else -> null
                    }
                    WorkoutUiModel(
                        workoutType = WorkoutTypeEnum.WEIGHT_TRAINING,
                        exerciseCardModel = CardUiModel(
                            name = it.name,
                            icon = it.icon ?: R.drawable.icon_dumbell,
                            reps = topSet?.reps,
                            weight = topSet?.weight
                        )
                    )
                }

                WorkoutTypeEnum.CALISTHENICS -> {
                    val mostRecentSession = when (val workoutSets = it.workoutPropertiesModel) {
                        is WorkoutPropertiesModel.CalisthenicsProps -> {
                            workoutSets.props.last()
                        }

                        else -> null
                    }
                    WorkoutUiModel(
                        workoutType = WorkoutTypeEnum.CALISTHENICS,
                        exerciseCardModel = CardUiModel(
                            name = it.name,
                            icon = it.icon ?: R.drawable.icon_person,
                            reps = mostRecentSession?.reps,
                            time = mostRecentSession?.time
                        )
                    )
                }

                WorkoutTypeEnum.CARDIO -> {
                    val mostRecentSession = when (val workoutSets = it.workoutPropertiesModel) {
                        is WorkoutPropertiesModel.CardioProps -> {
                            workoutSets.props.last()
                        }

                        else -> null
                    }
                    WorkoutUiModel(
                        workoutType = WorkoutTypeEnum.CARDIO,
                        exerciseCardModel = CardUiModel(
                            name = it.name,
                            icon = it.icon ?: R.drawable.icon_sprinting_person,
                            time = mostRecentSession?.time,
                            laps = mostRecentSession?.laps,
                            distance = mostRecentSession?.distance,
                            distanceType = mostRecentSession?.distanceType
                                ?: CardioDistanceType.MILES
                        )
                    )
                }
            }
        }
        return UiState.Success(workoutsMapped)
    }

    fun collectRealmWorkoutEntryFromDb() {
        viewModelScope.launch {
            workoutsFlow.collectLatest { realmWorkoutList ->
                val workoutList = realmUseCase.convertDatabaseRealmWorkoutEntryToUiUseCase(realmWorkoutList)
                updateHomeScreenState(
                    newHomeScreenState = homeScreenState.copy(
                        listOfWorkouts = workoutList,
                        listOfVisibleWorkouts = determineWorkout(listOfWorkouts = workoutList)
                    )
                )
            }
        }
    }
}
