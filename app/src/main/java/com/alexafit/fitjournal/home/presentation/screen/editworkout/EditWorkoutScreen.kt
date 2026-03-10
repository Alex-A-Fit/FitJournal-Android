package com.alexafit.fitjournal.home.presentation.screen.editworkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.domain.model.CalisthenicsModel
import com.alexafit.fitjournal.core.domain.model.CardioModel
import com.alexafit.fitjournal.core.domain.model.TimeModel
import com.alexafit.fitjournal.core.domain.model.WeightLiftingModel
import com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.DeleteButton
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.ClearAndSaveButtons
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutBanner
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.CalisthenicsListHeader
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.CalisthenicsWorkoutSets
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.CardioListHeader
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.CardioWorkoutSets
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.WeightLiftingListHeader
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.WeightLiftingWorkoutSets
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.DeleteWorkoutDialog
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.EditWorkoutSetDialog
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.TransparentLoadingScreenDialog
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog
import com.alexafit.fitjournal.core.presentation.commoncomponents.text.CommonTitleText
import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.alexafit.fitjournal.core.presentation.screens.LoadingScreen
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.core.util.extensions.toDoubleOrZero
import com.alexafit.fitjournal.core.util.extensions.toIntOrZero
import com.alexafit.fitjournal.core.util.state.UiState
import com.alexafit.fitjournal.home.presentation.components.datepicker.FitJournalDatePickerDialog
import com.alexafit.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.alexafit.fitjournal.home.presentation.model.state.EditWorkoutUiState
import com.alexafit.fitjournal.home.presentation.screen.editworkout.components.EditWorkoutErrorScreen
import com.alexafit.fitjournal.home.presentation.screen.editworkout.components.EditWorkoutSection

@Composable
fun EditWorkoutScreen(
    modifier: Modifier = Modifier,
    editWorkoutUiState: EditWorkoutUiState,
    showSnackBar: suspend (String) -> Unit,
    navigateToJournal: () -> Unit
) {
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showLoadingDialog by rememberSaveable {
        mutableStateOf(false)
    }
    // holds onto the id of the currently viewed workout
    // so we can delete it if need be
    var workoutUpForDeletion by rememberSaveable {
        mutableStateOf("")
    }

    var showEditWorkoutSetDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var editWorkoutSet: WorkoutTypeDialog by remember {
        mutableStateOf(WorkoutTypeDialog.None)
    }
    var showDatePickerDialog by rememberSaveable {
        mutableStateOf(false)
    }
    val dateUpdatedText = stringResource(id = R.string.text_date_updated)
    val dateFailedToUpdateText = stringResource(id = R.string.error_date_update_failed)

    when (val uiState = editWorkoutUiState.workout) {
        UiState.Empty -> {
            EditWorkoutErrorScreen(
                navigateToJournal = navigateToJournal
            )
        }

        is UiState.Error -> {
            EditWorkoutErrorScreen(
                navigateToJournal = navigateToJournal
            )
        }

        UiState.Loading -> {
            LoadingScreen()
        }

        UiState.None -> {
            EditWorkoutErrorScreen(
                navigateToJournal = navigateToJournal
            )
        }

        is UiState.Success -> {
            LaunchedEffect(key1 = uiState) {
                editWorkoutUiState.editWorkoutEvents(EditWorkoutEvents.UpdateDate(uiState.data.date))
            }
            // strings needed if we want to delete a workout or set
            val onDeleteFailedSnackBarText = stringResource(
                id = R.string.error_with_workout_being_deleted,
                uiState.data.workoutDetailsModel.name
            )
            val errorWithSetsBeingDeleted =
                stringResource(id = R.string.error_with_sets_being_deleted)

            // string for issues with adding sets
            val errorWithAddingSets = stringResource(id = R.string.error_with_adding_new_sets)
            val errorWithUpdatingSets = stringResource(id = R.string.error_with_updating_new_sets)

            // workout type as a string value
            val workoutTypeAsString =
                stringResource(id = uiState.data.workoutDetailsModel.workoutTypeEnum.stringId)
            workoutUpForDeletion = uiState.data.id

            if (showEditWorkoutSetDialog) {
                EditWorkoutSetDialog(
                    onDismissRequest = {
                        showEditWorkoutSetDialog = false
                    },
                    onSaveWorkoutPressed = {
                        showEditWorkoutSetDialog = false
                        showLoadingDialog = true
                        editWorkoutUiState.editWorkoutEvents(
                            EditWorkoutEvents.UpdateWorkoutListItem(
                                workoutTypeDialog = it,
                                workoutModel = uiState.data,
                                workoutType = workoutTypeAsString,
                                onUpdateErrorCallback = { showSnackBar(errorWithUpdatingSets) },
                                onSuccessfulUpdateCallback = {
                                    showLoadingDialog = false
                                }
                            )
                        )
                    },
                    workoutTypeDialog = editWorkoutSet
                )
            }

            if (showLoadingDialog) {
                TransparentLoadingScreenDialog(onBackPress = {})
            }

            if (showDeleteDialog) {
                DeleteWorkoutDialog(
                    workoutName = uiState.data.workoutDetailsModel.name,
                    workoutDate = uiState.data.date,
                    onDismiss = {
                        showDeleteDialog = false
                    },
                    onDelete = {
                        editWorkoutUiState.editWorkoutEvents(
                            EditWorkoutEvents.DeleteEntireWorkout(
                                workoutId = workoutUpForDeletion,
                                onDeleteErrorCallback = { showSnackBar(onDeleteFailedSnackBarText) },
                                onSuccessfulDeleteCallback = {
                                    navigateToJournal()
                                }
                            )
                        )
                        showDeleteDialog = false
                    }

                )
            }

            if (showDatePickerDialog) {
                FitJournalDatePickerDialog(
                    currentDate = editWorkoutUiState.localDateInMillis,
                    selectDate = { selectedDate ->
                        editWorkoutUiState.editWorkoutEvents(
                            EditWorkoutEvents.SelectDateFromDatePicker(
                                userSelectedDate = selectedDate,
                                onSuccessfulUpdateCallback = {
                                    showDatePickerDialog = false
                                    showSnackBar(dateUpdatedText)
                                },
                                workout = uiState.data,
                                workoutType = workoutTypeAsString,
                                onErrorCallback = {
                                    showDatePickerDialog = false
                                    showSnackBar(dateFailedToUpdateText)
                                }
                            )
                        )
                        showDatePickerDialog = false
                    },
                    dismissDialog = {
                        showDatePickerDialog = false
                    }
                )
            }

            LazyColumn(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    WorkoutTitle(
                        workoutName = uiState.data.workoutDetailsModel.name,
                        workoutType = stringResource(id = uiState.data.workoutDetailsModel.workoutTypeEnum.stringId)
                    )
                }
                item {
                    EditWorkoutBanner(
                        workoutDate = editWorkoutUiState.localDate,
                        openTimePicker = {
                            showDatePickerDialog = true
                        }
                    )
                }
                item {
                    EditWorkoutSection(
                        workoutType = uiState.data.workoutDetailsModel.workoutTypeEnum,
                        editWorkoutUiState = editWorkoutUiState
                    )
                }
                item {
                    ClearAndSaveButtons(
                        clearBtnOnClick = {
                            editWorkoutUiState.editWorkoutEvents(
                                EditWorkoutEvents.ClearWorkoutTextFields(
                                    uiState.data.workoutDetailsModel.workoutTypeEnum
                                )
                            )
                        },
                        saveBtnOnClick = {
                            when (uiState.data.workoutDetailsModel.workoutTypeEnum) {
                                WorkoutTypeEnum.WEIGHT_TRAINING -> {
                                    val newWeightTrainingItem = WeightLiftingModel(
                                        reps = editWorkoutUiState.reps.toIntOrZero(),
                                        sets = editWorkoutUiState.sets.toIntOrZero(),
                                        weight = editWorkoutUiState.weight.toDoubleOrZero(),
                                        weightType = editWorkoutUiState.weightType
                                    )
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.AddNewWeightTrainingSetToWorkout(
                                            newWeightLiftingItem = newWeightTrainingItem,
                                            workoutType = workoutTypeAsString,
                                            workoutModel = uiState.data,
                                            onAddErrorCallback = { showSnackBar(errorWithAddingSets) }
                                        )
                                    )
                                }

                                WorkoutTypeEnum.CALISTHENICS -> {
                                    val newCalisthenicItem = CalisthenicsModel(
                                        reps = editWorkoutUiState.reps.toIntOrZero(),
                                        sets = editWorkoutUiState.sets.toIntOrZero(),
                                        weight = editWorkoutUiState.weight.toDoubleOrNull(),
                                        weightType = editWorkoutUiState.weightType,
                                        time = if (editWorkoutUiState.hour.isEmpty() &&
                                            editWorkoutUiState.minute.isEmpty() &&
                                            editWorkoutUiState.second.isEmpty()
                                        ) {
                                            null
                                        } else {
                                            TimeModel(
                                                hours = editWorkoutUiState.hour,
                                                minutes = editWorkoutUiState.minute,
                                                seconds = editWorkoutUiState.second
                                            )
                                        }
                                    )
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.AddNewCalisthenicSetToWorkout(
                                            newCalisthenicItem = newCalisthenicItem,
                                            workoutType = workoutTypeAsString,
                                            workoutModel = uiState.data,
                                            onAddErrorCallback = { showSnackBar(errorWithAddingSets) }
                                        )
                                    )
                                }

                                WorkoutTypeEnum.CARDIO -> {
                                    val newCardioItem = CardioModel(
                                        distance = editWorkoutUiState.distance.toDoubleOrZero(),
                                        distanceType = editWorkoutUiState.distanceType,
                                        time = TimeModel(
                                            hours = editWorkoutUiState.hour,
                                            minutes = editWorkoutUiState.minute,
                                            seconds = editWorkoutUiState.second
                                        ),
                                        laps = editWorkoutUiState.laps.toDoubleOrNull()
                                    )
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.AddNewCardioSetToWorkout(
                                            newCardioItem = newCardioItem,
                                            workoutType = workoutTypeAsString,
                                            workoutModel = uiState.data,
                                            onAddErrorCallback = { showSnackBar(errorWithAddingSets) }
                                        )
                                    )
                                }
                            }
                        },
                        showSaveButton = when (uiState.data.workoutDetailsModel.workoutTypeEnum) {
                            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                                editWorkoutUiState.reps.isNotEmpty() &&
                                    editWorkoutUiState.sets.isNotEmpty() &&
                                    editWorkoutUiState.weight.isNotEmpty()
                            }

                            WorkoutTypeEnum.CALISTHENICS -> {
                                editWorkoutUiState.reps.isNotEmpty() &&
                                    editWorkoutUiState.sets.isNotEmpty()
                            }

                            WorkoutTypeEnum.CARDIO -> {
                                editWorkoutUiState.distance.isNotEmpty() &&
                                    (
                                        editWorkoutUiState.hour.isNotEmpty() ||
                                            editWorkoutUiState.minute.isNotEmpty() ||
                                            editWorkoutUiState.second.isNotEmpty()
                                        )
                            }
                        }
                    )
                }
                when (uiState.data.workoutDetailsModel.workoutTypeEnum) {
                    WorkoutTypeEnum.WEIGHT_TRAINING -> {
                        if (editWorkoutUiState.weightLiftingPropertyList.isNotEmpty()) {
                            item {
                                WeightLiftingListHeader()
                            }
                        }
                        itemsIndexed(editWorkoutUiState.weightLiftingPropertyList) { index, item ->
                            WeightLiftingWorkoutSets(
                                workout = item,
                                deleteSet = {
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                            index = index,
                                            workoutType = workoutTypeAsString,
                                            workoutModel = uiState.data,
                                            onDeleteErrorCallback = {
                                                showSnackBar(
                                                    errorWithSetsBeingDeleted
                                                )
                                            }
                                        )
                                    )
                                },
                                editSet = {
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.GetWorkoutSet(
                                            workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING,
                                            workoutPropertiesModel = uiState.data.workoutDetailsModel.workoutPropertiesModel,
                                            index = index,
                                            getWorkoutSetCallback = { workoutToEdit ->
                                                editWorkoutSet = workoutToEdit
                                                showEditWorkoutSetDialog = true
                                            }
                                        )
                                    )
                                }
                            )
                            if (index != editWorkoutUiState.weightLiftingPropertyList.lastIndex) {
                                Spacer(modifier = Modifier.height(Spacing.spacing8))
                            }
                        }
                    }

                    WorkoutTypeEnum.CALISTHENICS -> {
                        if (editWorkoutUiState.calisthenicsPropertyList.isNotEmpty()) {
                            item { CalisthenicsListHeader() }
                        }
                        itemsIndexed(editWorkoutUiState.calisthenicsPropertyList) { index, item ->
                            CalisthenicsWorkoutSets(
                                workout = item,
                                deleteSet = {
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                            index = index,
                                            workoutType = workoutTypeAsString,
                                            workoutModel = uiState.data,
                                            onDeleteErrorCallback = {
                                                showSnackBar(
                                                    errorWithSetsBeingDeleted
                                                )
                                            }
                                        )
                                    )
                                },
                                editSet = {
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.GetWorkoutSet(
                                            workoutTypeEnum = WorkoutTypeEnum.CALISTHENICS,
                                            workoutPropertiesModel = uiState.data.workoutDetailsModel.workoutPropertiesModel,
                                            index = index,
                                            getWorkoutSetCallback = { workoutToEdit ->
                                                editWorkoutSet = workoutToEdit
                                                showEditWorkoutSetDialog = true
                                            }
                                        )
                                    )
                                }
                            )
                            if (index != editWorkoutUiState.calisthenicsPropertyList.lastIndex) {
                                Spacer(modifier = Modifier.height(Spacing.spacing8))
                            }
                        }
                    }

                    WorkoutTypeEnum.CARDIO -> {
                        if (editWorkoutUiState.cardioPropertyList.isNotEmpty()) {
                            item { CardioListHeader() }
                        }
                        itemsIndexed(editWorkoutUiState.cardioPropertyList) { index, item ->
                            CardioWorkoutSets(
                                workout = item,
                                deleteSet = {
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                            index = index,
                                            workoutType = workoutTypeAsString,
                                            workoutModel = uiState.data,
                                            onDeleteErrorCallback = {
                                                showSnackBar(
                                                    errorWithSetsBeingDeleted
                                                )
                                            }
                                        )
                                    )
                                },
                                editSet = {
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.GetWorkoutSet(
                                            workoutTypeEnum = WorkoutTypeEnum.CARDIO,
                                            workoutPropertiesModel = uiState.data.workoutDetailsModel.workoutPropertiesModel,
                                            index = index,
                                            getWorkoutSetCallback = { workoutToEdit ->
                                                editWorkoutSet = workoutToEdit
                                                showEditWorkoutSetDialog = true
                                            }
                                        )
                                    )
                                }
                            )
                            if (index != editWorkoutUiState.cardioPropertyList.lastIndex) {
                                Spacer(modifier = Modifier.height(Spacing.spacing8))
                            }
                        }
                    }
                }
                item {
                    DeleteButton(
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
                            showDeleteDialog = true
                        },
                        text = stringResource(id = R.string.button_delete_workout)
                    )
                }
            }
        }
    }
}

@Composable
fun WorkoutTitle(
    workoutName: String,
    workoutType: String
) {
    CommonTitleText(
        titleText = stringResource(
            id = R.string.text_edit_workout_title,
            workoutName,
            workoutType
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing16)
    )
}
