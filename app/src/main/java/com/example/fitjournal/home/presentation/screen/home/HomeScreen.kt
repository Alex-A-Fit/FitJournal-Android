package com.example.fitjournal.home.presentation.screen.home

import androidx.compose.foundation.clickable
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
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.components.card.CalisthenicsCard
import com.example.fitjournal.home.presentation.components.card.CardioCard
import com.example.fitjournal.home.presentation.components.card.WeightLiftingCard
import com.example.fitjournal.home.presentation.components.datepicker.FitJournalDatePickerDialog
import com.example.fitjournal.home.presentation.mapper.mapToCalisthenicsUi
import com.example.fitjournal.home.presentation.mapper.mapToCardioUi
import com.example.fitjournal.home.presentation.mapper.mapToWeightLiftingUi
import com.example.fitjournal.home.presentation.model.events.HomeScreenEvents
import com.example.fitjournal.home.presentation.model.state.HomeScreenUiState

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenUiState,
    snackBarHostState: SnackbarHostState,
    lazyListState: LazyListState,
    navigateToDestination: (NavigationInterface) -> Unit,
    isBlurActive: Boolean
) {
    LaunchedEffect(key1 = homeScreenState.listOfVisibleWorkoutsUiState) {
        when (homeScreenState.listOfVisibleWorkoutsUiState) {
            UiState.None -> {
                homeScreenState.homeScreenEvents(HomeScreenEvents.CollectRealmWorkoutEntryFromDb)
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
                    homeScreenState.homeScreenEvents(
                        HomeScreenEvents.SelectDateFromDatePicker(
                            userSelectedDate = selectedDate,
                            snackBarHostState = snackBarHostState
                        )
                    )
                },
                dismissDialog = {
                    homeScreenState.homeScreenEvents(HomeScreenEvents.DismissDatePicker)
                }
            )
        }

        if (isFilterDialogShowing) {
            FilterWorkoutTypeDialog(
                properties = DialogProperties(),
                filterList = homeScreenState.filterList,
                onDismissDialog = {
                    homeScreenState.homeScreenEvents(HomeScreenEvents.DismissFilterExercisesDialog)
                },
                onConfirmDialog = { listOfWorkoutTypes ->
                    homeScreenState.homeScreenEvents(
                        HomeScreenEvents.OnConfirmFilterExercisesDialog(
                            listOfWorkoutTypes
                        )
                    )
                },
                clearFilterList = {
                    homeScreenState.homeScreenEvents(HomeScreenEvents.ClearFilterExercisesDialog)
                }
            )
        }
        when (val workoutList = homeScreenState.listOfVisibleWorkoutsUiState) {
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
                        when (workout.workoutDetailsUiModel.workoutType) {
                            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                                WeightLiftingCard(
                                    weightLiftingUi = workout.workoutDetailsUiModel.mapToWeightLiftingUi(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navigateToDestination(
                                                NavigationInterface.NavigateToEditWorkout(
                                                    workout.id
                                                )
                                            )
                                        }
                                )
                            }

                            WorkoutTypeEnum.CALISTHENICS -> {
                                CalisthenicsCard(
                                    calisthenicsUi = workout.workoutDetailsUiModel.mapToCalisthenicsUi(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navigateToDestination(
                                                NavigationInterface.NavigateToEditWorkout(
                                                    workout.id
                                                )
                                            )
                                        }
                                )
                            }

                            WorkoutTypeEnum.CARDIO -> {
                                CardioCard(
                                    cardioUi = workout.workoutDetailsUiModel.mapToCardioUi(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navigateToDestination(
                                                NavigationInterface.NavigateToEditWorkout(
                                                    workout.id
                                                )
                                            )
                                        }
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
