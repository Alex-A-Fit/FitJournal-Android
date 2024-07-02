package com.alexafit.fitjournal.home.presentation.screen.home

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.AddToJournalButton
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.ViewTutorialDialog
import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.alexafit.fitjournal.core.presentation.navigation.NavigationDirectionInterface
import com.alexafit.fitjournal.core.presentation.screens.LoadingScreen
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.core.util.state.UiState
import com.alexafit.fitjournal.home.presentation.components.card.CalisthenicsCard
import com.alexafit.fitjournal.home.presentation.components.card.CardioCard
import com.alexafit.fitjournal.home.presentation.components.card.WeightLiftingCard
import com.alexafit.fitjournal.home.presentation.components.datepicker.FitJournalDatePickerDialog
import com.alexafit.fitjournal.home.presentation.mapper.mapToCalisthenicsUi
import com.alexafit.fitjournal.home.presentation.mapper.mapToCardioUi
import com.alexafit.fitjournal.home.presentation.mapper.mapToWeightLiftingUi
import com.alexafit.fitjournal.home.presentation.model.events.HomeScreenEvents
import com.alexafit.fitjournal.home.presentation.model.state.HomeScreenUiState
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenUiState,
    lazyListState: LazyListState,
    navigateToDestination: (NavigationDirectionInterface) -> Unit,
    isBlurActive: Boolean,
    setChildFabsVisibility: (Boolean) -> Unit,
    showSnackBar: suspend (String) -> Unit
) {
    LaunchedEffect(key1 = true) {
        homeScreenState.homeScreenEvents(HomeScreenEvents.SyncRealmWorkoutEntryFromDb)
    }

    val isDatePickerDialogShowing by rememberSaveable(homeScreenState.isDatePickerDialogShowing) {
        mutableStateOf(homeScreenState.isDatePickerDialogShowing)
    }
    var isHelpDialogShowing by rememberSaveable(homeScreenState.isHelpDialogShowing) {
        mutableStateOf(homeScreenState.isHelpDialogShowing)
    }
    val clickIndication = LocalIndication.current
    val dateUpdatedText = stringResource(id = R.string.text_date_updated)
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
    ) {
        if (isDatePickerDialogShowing) {
            FitJournalDatePickerDialog(
                currentDate = homeScreenState.currentDateInMillis,
                selectDate = { selectedDate ->
                    homeScreenState.homeScreenEvents(
                        HomeScreenEvents.SelectDateFromDatePicker(
                            userSelectedDate = selectedDate
                        )
                    )
                    coroutineScope.launch {
                        showSnackBar(dateUpdatedText)
                    }
                },
                dismissDialog = {
                    homeScreenState.homeScreenEvents(HomeScreenEvents.DismissDatePicker)
                }
            )
        }

        if (isHelpDialogShowing) {
            ViewTutorialDialog(
                properties = DialogProperties(),
                onDismissDialog = {
                    homeScreenState.homeScreenEvents(
                        HomeScreenEvents.UpdateHelpDialog(
                            isDialogShowing = false,
                            onCallback = {
                                isHelpDialogShowing = false
                            }
                        )
                    )
                },
                onConfirmDialog = {
                    homeScreenState.homeScreenEvents(
                        HomeScreenEvents.UpdateHelpDialog(
                            isDialogShowing = false,
                            onCallback = {
                                isHelpDialogShowing = false
                                navigateToDestination(NavigationDirectionInterface.NavigateToOnboarding)
                            }
                        )
                    )
                }
            )
        }
        when (val workoutList = homeScreenState.listOfVisibleWorkoutsUiState) {
            UiState.Loading, UiState.None -> {
                LoadingScreen()
            }

            UiState.Empty, is UiState.Error -> {
                HomeEmptyScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateToAddWorkoutScreen = {
                        navigateToDestination(NavigationDirectionInterface.NavigateToAddWorkout(workoutDate = homeScreenState.currentDate))
                    }
                )
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
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication =
                                            if (isBlurActive) null else clickIndication
                                        ) {
                                            if (isBlurActive) {
                                                setChildFabsVisibility(false)
                                            } else {
                                                navigateToDestination(
                                                    NavigationDirectionInterface.NavigateToEditWorkout(
                                                        workout.id
                                                    )
                                                )
                                            }
                                        }
                                )
                            }

                            WorkoutTypeEnum.CALISTHENICS -> {
                                CalisthenicsCard(
                                    calisthenicsUi = workout.workoutDetailsUiModel.mapToCalisthenicsUi(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = if (isBlurActive) null else clickIndication
                                        ) {
                                            if (isBlurActive) {
                                                setChildFabsVisibility(false)
                                            } else {
                                                navigateToDestination(
                                                    NavigationDirectionInterface.NavigateToEditWorkout(
                                                        workout.id
                                                    )
                                                )
                                            }
                                        }
                                )
                            }

                            WorkoutTypeEnum.CARDIO -> {
                                CardioCard(
                                    cardioUi = workout.workoutDetailsUiModel.mapToCardioUi(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = if (isBlurActive) null else clickIndication
                                        ) {
                                            if (isBlurActive) {
                                                setChildFabsVisibility(false)
                                            } else {
                                                navigateToDestination(
                                                    NavigationDirectionInterface.NavigateToEditWorkout(
                                                        workout.id
                                                    )
                                                )
                                            }
                                        }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(Spacing.spacing12))
                    }
                    item {
                        AddToJournalButton(
                            navigate = {
                                navigateToDestination(
                                    NavigationDirectionInterface.NavigateToAddWorkout(
                                        workoutDate = homeScreenState.currentDate
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(Spacing.spacing96))
                    }
                }
            }
        }
    }
}
