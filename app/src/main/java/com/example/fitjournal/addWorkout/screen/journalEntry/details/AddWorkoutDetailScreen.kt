package com.example.fitjournal.addWorkout.screen.journalEntry.details

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
import com.example.fitjournal.addWorkout.model.AddWorkoutDetailUiState
import com.example.fitjournal.addWorkout.model.events.AddWorkoutDetailEvents
import com.example.fitjournal.addWorkout.screen.journalEntry.details.components.AddWorkoutErrorScreen
import com.example.fitjournal.addWorkout.screen.journalEntry.details.components.AddWorkoutSection
import com.example.fitjournal.core.data.model.results.Result
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
import kotlinx.coroutines.launch

@Composable
fun AddWorkoutDetailScreen(
    modifier: Modifier,
    addWorkoutDetailUiState: AddWorkoutDetailUiState,
    showSnackbar: suspend (String) -> Unit,
    navigateBackToAddWorkoutScreen: () -> Unit,
    navigateBackToJournal: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var showLoadingScreenDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var enableSaveButton by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(
        key1 = addWorkoutDetailUiState.calisthenicsPropertyList,
        key2 = addWorkoutDetailUiState.cardioPropertyList,
        key3 = addWorkoutDetailUiState.weightLiftingPropertyList
    ) {
        enableSaveButton = addWorkoutDetailUiState.calisthenicsPropertyList.isNotEmpty() ||
            addWorkoutDetailUiState.cardioPropertyList.isNotEmpty() ||
            addWorkoutDetailUiState.weightLiftingPropertyList.isNotEmpty()
    }
    val workoutCanceled = stringResource(id = R.string.error_add_workout_canceled)
    val workoutSuccessfullyAdded = stringResource(
        id = R.string.text_workout_successfully_added_to_journal,
        addWorkoutDetailUiState.workoutName
    )
    if (showLoadingScreenDialog) {
        TransparentLoadingScreenDialog(
            onBackPress = {
                addWorkoutDetailUiState.addWorkoutDetailEvents(AddWorkoutDetailEvents.StopAddWorkoutJob)
                showLoadingScreenDialog = false
                coroutineScope.launch {
                    showSnackbar(workoutCanceled)
                }
            }
        )
    }
    if (addWorkoutDetailUiState.workoutName.isEmpty() || addWorkoutDetailUiState.workoutType.isEmpty() || addWorkoutDetailUiState.workoutTypeEnum == null) {
        AddWorkoutErrorScreen(navigateToAddWorkoutScreen = navigateBackToAddWorkoutScreen)
    } else {
        LaunchedEffect(key1 = addWorkoutDetailUiState.workoutName) {
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.ClearViewModelState
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
                    workoutName = addWorkoutDetailUiState.workoutName,
                    workoutType = addWorkoutDetailUiState.workoutType
                )
            }
            item {
                EditWorkoutBanner(workoutDate = addWorkoutDetailUiState.date)
            }
            item {
                AddWorkoutSection(
                    workoutType = addWorkoutDetailUiState.workoutTypeEnum,
                    addWorkoutDetailUiState = addWorkoutDetailUiState
                )
            }
            item {
                // clear and save buttons for adding sets to workout
                ClearAndSaveButtons(
                    clearBtnOnClick = {
                        addWorkoutDetailUiState.addWorkoutDetailEvents(
                            AddWorkoutDetailEvents.ClearWorkoutTextFields(
                                addWorkoutDetailUiState.workoutTypeEnum
                            )
                        )
                    },
                    saveBtnOnClick = {
                        when (addWorkoutDetailUiState.workoutTypeEnum) {
                            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                                val newWeightTrainingItem = WeightLiftingModel(
                                    reps = addWorkoutDetailUiState.reps.toIntOrZero(),
                                    sets = addWorkoutDetailUiState.sets.toIntOrZero(),
                                    weight = addWorkoutDetailUiState.weight.toDoubleOrZero(),
                                    weightType = addWorkoutDetailUiState.weightType
                                )
                                addWorkoutDetailUiState.addWorkoutDetailEvents(
                                    AddWorkoutDetailEvents.AddNewWeightTrainingSetToWorkout(
                                        newWeightLiftingItem = newWeightTrainingItem,
                                        workoutType = addWorkoutDetailUiState.workoutType,
                                        onAddErrorCallback = { showSnackbar(errorWithAddingSets) }
                                    )
                                )
                            }

                            WorkoutTypeEnum.CALISTHENICS -> {
                                val newCalisthenicItem = CalisthenicsModel(
                                    reps = addWorkoutDetailUiState.reps.toIntOrZero(),
                                    sets = addWorkoutDetailUiState.sets.toIntOrZero(),
                                    weight = addWorkoutDetailUiState.weight.toDoubleOrNull(),
                                    weightType = addWorkoutDetailUiState.weightType,
                                    time = if (addWorkoutDetailUiState.hour.isEmpty() &&
                                        addWorkoutDetailUiState.minute.isEmpty() &&
                                        addWorkoutDetailUiState.second.isEmpty()
                                    ) {
                                        null
                                    } else {
                                        TimeModel(
                                            hours = addWorkoutDetailUiState.hour,
                                            minutes = addWorkoutDetailUiState.minute,
                                            seconds = addWorkoutDetailUiState.second
                                        )
                                    }
                                )
                                addWorkoutDetailUiState.addWorkoutDetailEvents(
                                    AddWorkoutDetailEvents.AddNewCalisthenicSetToWorkout(
                                        newCalisthenicItem = newCalisthenicItem,
                                        workoutType = addWorkoutDetailUiState.workoutType,
                                        onAddErrorCallback = { showSnackbar(errorWithAddingSets) }
                                    )
                                )
                            }

                            WorkoutTypeEnum.CARDIO -> {
                                val newCardioItem = CardioModel(
                                    distance = addWorkoutDetailUiState.distance.toDoubleOrZero(),
                                    distanceType = addWorkoutDetailUiState.distanceType,
                                    time = TimeModel(
                                        hours = addWorkoutDetailUiState.hour,
                                        minutes = addWorkoutDetailUiState.minute,
                                        seconds = addWorkoutDetailUiState.second
                                    ),
                                    laps = addWorkoutDetailUiState.laps.toDoubleOrNull()
                                )
                                addWorkoutDetailUiState.addWorkoutDetailEvents(
                                    AddWorkoutDetailEvents.AddNewCardioSetToWorkout(
                                        newCardioItem = newCardioItem,
                                        workoutType = addWorkoutDetailUiState.workoutType,
                                        onAddErrorCallback = { showSnackbar(errorWithAddingSets) }
                                    )
                                )
                            }
                        }
                    },
                    showSaveButton = when (addWorkoutDetailUiState.workoutTypeEnum) {
                        WorkoutTypeEnum.WEIGHT_TRAINING -> {
                            (
                                addWorkoutDetailUiState.reps.isNotEmpty() &&
                                    addWorkoutDetailUiState.sets.isNotEmpty() &&
                                    addWorkoutDetailUiState.weight.isNotEmpty()
                                )
                        }

                        WorkoutTypeEnum.CALISTHENICS -> {
                            (
                                addWorkoutDetailUiState.reps.isNotEmpty() &&
                                    addWorkoutDetailUiState.sets.isNotEmpty()
                                )
                        }

                        WorkoutTypeEnum.CARDIO -> {
                            (
                                addWorkoutDetailUiState.distance.isNotEmpty() &&
                                    (
                                        addWorkoutDetailUiState.hour.isNotEmpty() ||
                                            addWorkoutDetailUiState.minute.isNotEmpty() ||
                                            addWorkoutDetailUiState.second.isNotEmpty()
                                        )
                                )
                        }
                    }
                )
            }
            when (addWorkoutDetailUiState.workoutTypeEnum) {
                WorkoutTypeEnum.WEIGHT_TRAINING -> {
                    if (addWorkoutDetailUiState.weightLiftingPropertyList.isNotEmpty()) {
                        item {
                            WeightLiftingListHeader()
                        }
                    }
                    itemsIndexed(addWorkoutDetailUiState.weightLiftingPropertyList) { index, item ->
                        WeightLiftingWorkoutSets(
                            workout = item,
                            index = index,
                            deleteSet = {
                                addWorkoutDetailUiState.addWorkoutDetailEvents(
                                    AddWorkoutDetailEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                        index = index,
                                        workoutType = addWorkoutDetailUiState.workoutTypeEnum,
                                        onDeleteErrorCallback = {
                                            showSnackbar(errorWithSetsBeingDeleted)
                                        }
                                    )
                                )
                            },
                            editSet = {}
                        )
                        if (index != addWorkoutDetailUiState.weightLiftingPropertyList.lastIndex) {
                            Spacer(modifier = Modifier.height(Spacing.spacing8))
                        }
                    }
                }

                WorkoutTypeEnum.CALISTHENICS -> {
                    if (addWorkoutDetailUiState.calisthenicsPropertyList.isNotEmpty()) {
                        item { CalisthenicsListHeader() }
                    }
                    itemsIndexed(addWorkoutDetailUiState.calisthenicsPropertyList) { index, item ->
                        CalisthenicsWorkoutSets(
                            workout = item,
                            index = index,
                            deleteSet = {
                                addWorkoutDetailUiState.addWorkoutDetailEvents(
                                    AddWorkoutDetailEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                        index = index,
                                        workoutType = addWorkoutDetailUiState.workoutTypeEnum,
                                        onDeleteErrorCallback = {
                                            showSnackbar(errorWithSetsBeingDeleted)
                                        }
                                    )
                                )
                            },
                            editSet = {}
                        )
                        if (index != addWorkoutDetailUiState.calisthenicsPropertyList.lastIndex) {
                            Spacer(modifier = Modifier.height(Spacing.spacing8))
                        }
                    }
                }

                WorkoutTypeEnum.CARDIO -> {
                    if (addWorkoutDetailUiState.cardioPropertyList.isNotEmpty()) {
                        item { CardioListHeader() }
                    }
                    itemsIndexed(addWorkoutDetailUiState.cardioPropertyList) { index, item ->
                        CardioWorkoutSets(
                            workout = item,
                            index = index,
                            deleteSet = {
                                addWorkoutDetailUiState.addWorkoutDetailEvents(
                                    AddWorkoutDetailEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                        index = index,
                                        workoutType = addWorkoutDetailUiState.workoutTypeEnum,
                                        onDeleteErrorCallback = {
                                            showSnackbar(errorWithSetsBeingDeleted)
                                        }
                                    )
                                )
                            },
                            editSet = {}
                        )
                        if (index != addWorkoutDetailUiState.cardioPropertyList.lastIndex) {
                            Spacer(modifier = Modifier.height(Spacing.spacing8))
                        }
                    }
                }
            }
            item {
                // save button for adding workout to journal
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
                        addWorkoutDetailUiState.addWorkoutDetailEvents(
                            AddWorkoutDetailEvents.AddWorkoutToRealm(
                                callback = { realmResult ->
                                    when (realmResult) {
                                        Result.SUCCESS -> {
                                            showLoadingScreenDialog = false
                                            navigateBackToJournal(workoutSuccessfullyAdded)
                                        }

                                        Result.FAILURE -> {
                                            showLoadingScreenDialog = false
                                            coroutineScope.launch {
                                                showSnackbar(workoutSuccessfullyAdded)
                                            }
                                        }
                                    }
                                }
                            )
                        )
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
