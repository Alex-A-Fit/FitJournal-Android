package com.example.fitjournal.home.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.FilterWorkoutTypeDialog
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.components.card.CalisthenicsCard
import com.example.fitjournal.home.presentation.components.card.CardioCard
import com.example.fitjournal.home.presentation.components.card.WeightLiftingCard
import com.example.fitjournal.home.presentation.components.datepicker.FitJournalDatePickerDialog
import com.example.fitjournal.home.presentation.model.events.HomeScreenEvents
import com.example.fitjournal.home.presentation.model.state.HomeScreenUiState

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenUiState,
    snackBarHostState: SnackbarHostState,
    lazyListState: LazyListState,
    isBlurActive: Boolean,
    homeScreenEvents: (HomeScreenEvents) -> Unit
) {
    LaunchedEffect(key1 = homeScreenState.listOfVisibleWorkouts) {
        when (homeScreenState.listOfVisibleWorkouts) {
            UiState.None -> {
                homeScreenEvents(HomeScreenEvents.CollectRealmWorkoutEntryFromDb)
            }
            else -> Unit
        }
    }

    var isDatePickerDialogShowing by rememberSaveable {
        mutableStateOf(homeScreenState.isDatePickerDialogShowing)
    }
    var isFilterDialogShowing by remember {
        mutableStateOf(homeScreenState.isFilterDialogShowing)
    }

    isFilterDialogShowing = homeScreenState.isFilterDialogShowing
    isDatePickerDialogShowing = homeScreenState.isDatePickerDialogShowing
    Box(
        modifier = modifier
    ) {
        if (isDatePickerDialogShowing) {
            FitJournalDatePickerDialog(
                currentDate = homeScreenState.currentDateInMillis,
                selectDate = { selectedDate ->
                    homeScreenEvents(
                        HomeScreenEvents.SelectDateFromDatePicker(
                            userSelectedDate = selectedDate,
                            snackBarHostState = snackBarHostState
                        )
                    )
                },
                dismissDialog = {
                    homeScreenEvents(HomeScreenEvents.DismissDatePicker)
                }
            )
        }

        if (isFilterDialogShowing) {
            FilterWorkoutTypeDialog(
                properties = DialogProperties(),
                workoutList = homeScreenState.filterDialogList,
                onDismissDialog = {
                    homeScreenEvents(HomeScreenEvents.DismissFilterExercisesDialog)
                },
                onConfirmDialog = { listOfWorkoutTypes ->
                    homeScreenEvents(
                        HomeScreenEvents.OnConfirmFilterExercisesDialog(
                            listOfWorkoutTypes
                        )
                    )
                }
            )
        }
        when (val workoutList = homeScreenState.listOfVisibleWorkouts) {
            UiState.Loading -> {
                // need to provide loading animation of some sorts
                Unit
            }
            UiState.Empty, is UiState.Error -> {
                // need to provide empty state of some sorts for empty and error
                Unit
            }
            UiState.None -> {
                // none should be defaulted to loading
            }
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    state = lazyListState,
                    userScrollEnabled = !isBlurActive,
                    contentPadding = PaddingValues(all = Spacing.spacing16)
                ) {
                    items(items = workoutList.data) { workout ->
                        when (workout.workoutType) {
                            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                                WeightLiftingCard(
                                    reps = workout.exerciseCardModel.reps,
                                    weight = workout.exerciseCardModel.weight,
                                    name = workout.exerciseCardModel.name,
                                    icon = workout.exerciseCardModel.icon,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            WorkoutTypeEnum.CALISTHENICS -> {
                                CalisthenicsCard(
                                    reps = workout.exerciseCardModel.reps,
                                    time = workout.exerciseCardModel.time,
                                    name = workout.exerciseCardModel.name,
                                    icon = workout.exerciseCardModel.icon,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            WorkoutTypeEnum.CARDIO -> {
                                CardioCard(
                                    name = workout.exerciseCardModel.name,
                                    icon = workout.exerciseCardModel.icon,
                                    distance = workout.exerciseCardModel.distance,
                                    distanceType = workout.exerciseCardModel.distanceType,
                                    time = workout.exerciseCardModel.time,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(Spacing.spacing12))
                    }
                }
            }
        }
    }
}
