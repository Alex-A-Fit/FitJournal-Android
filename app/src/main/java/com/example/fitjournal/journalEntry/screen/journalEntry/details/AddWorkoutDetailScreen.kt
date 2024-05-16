package com.example.fitjournal.journalEntry.screen.journalEntry.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.ClearAndSaveButtons
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutBanner
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.CalisthenicsListHeader
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.CalisthenicsWorkoutSets
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.CardioListHeader
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.CardioWorkoutSets
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.WeightLiftingListHeader
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.WeightLiftingWorkoutSets
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.TransparentLoadingScreenDialog
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.DisabledBackgroundGray
import com.example.fitjournal.core.presentation.theme.MediumGray
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.util.extensions.toDoubleOrZero
import com.example.fitjournal.core.util.extensions.toIntOrZero
import com.example.fitjournal.home.presentation.screen.editworkout.WorkoutTitle
import com.example.fitjournal.journalEntry.model.JournalEntryDetailsUiState
import com.example.fitjournal.journalEntry.model.events.JournalEntryDetailsEvents
import com.example.fitjournal.journalEntry.screen.journalEntry.details.components.AddWorkoutErrorScreen
import com.example.fitjournal.journalEntry.screen.journalEntry.details.components.AddWorkoutSection
import kotlinx.coroutines.launch

@Composable
fun AddWorkoutDetailScreen(
    modifier: Modifier,
    journalEntryDetailsUiState: JournalEntryDetailsUiState,
    showSnackbar: suspend (String) -> Unit,
    navigateToAddWorkoutScreen: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var showLoadingScreenDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var enableSaveButton by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(
        key1 = journalEntryDetailsUiState.calisthenicsPropertyList,
        key2 = journalEntryDetailsUiState.cardioPropertyList,
        key3 = journalEntryDetailsUiState.weightLiftingPropertyList
    ) {
        enableSaveButton = journalEntryDetailsUiState.calisthenicsPropertyList.isNotEmpty() ||
            journalEntryDetailsUiState.cardioPropertyList.isNotEmpty() ||
            journalEntryDetailsUiState.weightLiftingPropertyList.isNotEmpty()
    }
    val workoutCanceled = stringResource(id = R.string.error_add_workout_canceled)
    if (showLoadingScreenDialog) {
        TransparentLoadingScreenDialog(
            onBackPress = {
                // cancel workout
                showLoadingScreenDialog = false
                coroutineScope.launch {
                    showSnackbar(workoutCanceled)
                }
            }
        )
    }
    if (journalEntryDetailsUiState.workoutName.isEmpty() || journalEntryDetailsUiState.workoutType.isEmpty() || journalEntryDetailsUiState.workoutTypeEnum == null) {
        AddWorkoutErrorScreen(navigateToAddWorkoutScreen = navigateToAddWorkoutScreen)
    } else {
        LaunchedEffect(key1 = journalEntryDetailsUiState.workoutName) {
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.ClearViewModelState
            )
        }
        // strings needed if we want to delete a set
        val errorWithSetsBeingDeleted =
            stringResource(id = R.string.error_with_sets_being_deleted)

        // string for issues with adding sets
        val errorWithAddingSets = stringResource(id = R.string.error_with_adding_new_sets)

        LazyColumn(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                WorkoutTitle(
                    workoutName = journalEntryDetailsUiState.workoutName,
                    workoutType = journalEntryDetailsUiState.workoutType
                )
            }
            item {
                EditWorkoutBanner(workoutDate = journalEntryDetailsUiState.date)
            }
            item {
                AddWorkoutSection(
                    workoutType = journalEntryDetailsUiState.workoutTypeEnum,
                    journalEntryDetailsUiState = journalEntryDetailsUiState
                )
            }
            item {
                ClearAndSaveButtons(
                    clearBtnOnClick = {
                        journalEntryDetailsUiState.journalEntryDetailsEvents(
                            JournalEntryDetailsEvents.ClearWorkoutTextFields(
                                journalEntryDetailsUiState.workoutTypeEnum
                            )
                        )
                    },
                    saveBtnOnClick = {
                        when (journalEntryDetailsUiState.workoutTypeEnum) {
                            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                                val newWeightTrainingItem = WeightLiftingModel(
                                    reps = journalEntryDetailsUiState.reps.toIntOrZero(),
                                    sets = journalEntryDetailsUiState.sets.toIntOrZero(),
                                    weight = journalEntryDetailsUiState.weight.toDoubleOrZero(),
                                    weightType = journalEntryDetailsUiState.weightType
                                )
                                journalEntryDetailsUiState.journalEntryDetailsEvents(
                                    JournalEntryDetailsEvents.AddNewWeightTrainingSetToWorkout(
                                        newWeightLiftingItem = newWeightTrainingItem,
                                        workoutType = journalEntryDetailsUiState.workoutType,
                                        onAddErrorCallback = { showSnackbar(errorWithAddingSets) }
                                    )
                                )
                            }

                            WorkoutTypeEnum.CALISTHENICS -> {
                                val newCalisthenicItem = CalisthenicsModel(
                                    reps = journalEntryDetailsUiState.reps.toIntOrZero(),
                                    sets = journalEntryDetailsUiState.sets.toIntOrZero(),
                                    weight = journalEntryDetailsUiState.weight.toDoubleOrNull(),
                                    weightType = journalEntryDetailsUiState.weightType,
                                    time = if (journalEntryDetailsUiState.hour.isEmpty() &&
                                        journalEntryDetailsUiState.minute.isEmpty() &&
                                        journalEntryDetailsUiState.second.isEmpty()
                                    ) {
                                        null
                                    } else {
                                        TimeModel(
                                            hours = journalEntryDetailsUiState.hour,
                                            minutes = journalEntryDetailsUiState.minute,
                                            seconds = journalEntryDetailsUiState.second
                                        )
                                    }
                                )
                                journalEntryDetailsUiState.journalEntryDetailsEvents(
                                    JournalEntryDetailsEvents.AddNewCalisthenicSetToWorkout(
                                        newCalisthenicItem = newCalisthenicItem,
                                        workoutType = journalEntryDetailsUiState.workoutType,
                                        onAddErrorCallback = { showSnackbar(errorWithAddingSets) }
                                    )
                                )
                            }

                            WorkoutTypeEnum.CARDIO -> {
                                val newCardioItem = CardioModel(
                                    distance = journalEntryDetailsUiState.distance.toDoubleOrZero(),
                                    distanceType = journalEntryDetailsUiState.distanceType,
                                    time = TimeModel(
                                        hours = journalEntryDetailsUiState.hour,
                                        minutes = journalEntryDetailsUiState.minute,
                                        seconds = journalEntryDetailsUiState.second
                                    ),
                                    laps = journalEntryDetailsUiState.laps.toDoubleOrNull()
                                )
                                journalEntryDetailsUiState.journalEntryDetailsEvents(
                                    JournalEntryDetailsEvents.AddNewCardioSetToWorkout(
                                        newCardioItem = newCardioItem,
                                        workoutType = journalEntryDetailsUiState.workoutType,
                                        onAddErrorCallback = { showSnackbar(errorWithAddingSets) }
                                    )
                                )
                            }
                        }
                    },
                    showSaveButton = when (journalEntryDetailsUiState.workoutTypeEnum) {
                        WorkoutTypeEnum.WEIGHT_TRAINING -> {
                            (
                                journalEntryDetailsUiState.reps.isNotEmpty() &&
                                    journalEntryDetailsUiState.sets.isNotEmpty() &&
                                    journalEntryDetailsUiState.weight.isNotEmpty()
                                )
                        }

                        WorkoutTypeEnum.CALISTHENICS -> {
                            (
                                journalEntryDetailsUiState.reps.isNotEmpty() &&
                                    journalEntryDetailsUiState.sets.isNotEmpty()
                                )
                        }

                        WorkoutTypeEnum.CARDIO -> {
                            (
                                journalEntryDetailsUiState.distance.isNotEmpty() &&
                                    (
                                        journalEntryDetailsUiState.hour.isNotEmpty() ||
                                            journalEntryDetailsUiState.minute.isNotEmpty() ||
                                            journalEntryDetailsUiState.second.isNotEmpty()
                                        )
                                )
                        }
                    }
                )
            }
            when (journalEntryDetailsUiState.workoutTypeEnum) {
                WorkoutTypeEnum.WEIGHT_TRAINING -> {
                    if (journalEntryDetailsUiState.weightLiftingPropertyList.isNotEmpty()) {
                        item {
                            WeightLiftingListHeader()
                        }
                    }
                    itemsIndexed(journalEntryDetailsUiState.weightLiftingPropertyList) { index, item ->
                        WeightLiftingWorkoutSets(
                            workout = item,
                            index = index,
                            deleteSet = {
                                journalEntryDetailsUiState.journalEntryDetailsEvents(
                                    JournalEntryDetailsEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                        index = index,
                                        workoutType = journalEntryDetailsUiState.workoutTypeEnum,
                                        onDeleteErrorCallback = {
                                            showSnackbar(errorWithSetsBeingDeleted)
                                        }
                                    )
                                )
                            },
                            editSet = {}
                        )
                        if (index != journalEntryDetailsUiState.weightLiftingPropertyList.lastIndex) {
                            Spacer(modifier = Modifier.height(Spacing.spacing8))
                        }
                    }
                }

                WorkoutTypeEnum.CALISTHENICS -> {
                    if (journalEntryDetailsUiState.calisthenicsPropertyList.isNotEmpty()) {
                        item { CalisthenicsListHeader() }
                    }
                    itemsIndexed(journalEntryDetailsUiState.calisthenicsPropertyList) { index, item ->
                        CalisthenicsWorkoutSets(
                            workout = item,
                            index = index,
                            deleteSet = {
                                journalEntryDetailsUiState.journalEntryDetailsEvents(
                                    JournalEntryDetailsEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                        index = index,
                                        workoutType = journalEntryDetailsUiState.workoutTypeEnum,
                                        onDeleteErrorCallback = {
                                            showSnackbar(errorWithSetsBeingDeleted)
                                        }
                                    )
                                )
                            },
                            editSet = {}
                        )
                        if (index != journalEntryDetailsUiState.calisthenicsPropertyList.lastIndex) {
                            Spacer(modifier = Modifier.height(Spacing.spacing8))
                        }
                    }
                }

                WorkoutTypeEnum.CARDIO -> {
                    if (journalEntryDetailsUiState.cardioPropertyList.isNotEmpty()) {
                        item { CardioListHeader() }
                    }
                    itemsIndexed(journalEntryDetailsUiState.cardioPropertyList) { index, item ->
                        CardioWorkoutSets(
                            workout = item,
                            index = index,
                            deleteSet = {
                                journalEntryDetailsUiState.journalEntryDetailsEvents(
                                    JournalEntryDetailsEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                        index = index,
                                        workoutType = journalEntryDetailsUiState.workoutTypeEnum,
                                        onDeleteErrorCallback = {
                                            showSnackbar(errorWithSetsBeingDeleted)
                                        }
                                    )
                                )
                            },
                            editSet = {}
                        )
                        if (index != journalEntryDetailsUiState.cardioPropertyList.lastIndex) {
                            Spacer(modifier = Modifier.height(Spacing.spacing8))
                        }
                    }
                }
            }
            item {
                SaveButton(
                    text = stringResource(id = R.string.button_save_workout),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Spacing.spacing48,
                            vertical = Spacing.spacing32
                        ),
                    textModifier = Modifier.padding(
                        horizontal = Spacing.spacing32,
                        vertical = Spacing.spacing4
                    ),
                    onClick = {
                        showLoadingScreenDialog = true
                        // call viewmodel fn and pass back result
                    },
                    buttonColor = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = DisabledBackgroundGray,
                        disabledContentColor = MediumGray
                    ),
                    isEnabled = enableSaveButton
                )
            }
        }
    }
}
