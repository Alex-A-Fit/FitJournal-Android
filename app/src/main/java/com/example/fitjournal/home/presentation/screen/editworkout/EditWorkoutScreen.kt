package com.example.fitjournal.home.presentation.screen.editworkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.DeleteButton
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.ClearAndSaveButtons
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutBanner
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.WeightLiftingListHeader
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.WeightLiftingWorkoutSets
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.DeleteWorkoutDialog
import com.example.fitjournal.core.presentation.commoncomponents.text.CommonSubtitleText
import com.example.fitjournal.core.presentation.commoncomponents.text.CommonTitleText
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.screens.LoadingScreen
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.util.extensions.toDoubleOrZero
import com.example.fitjournal.core.util.extensions.toIntOrZero
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.home.presentation.components.editworkout.EditWorkoutErrorScreen
import com.example.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutScreen(
    modifier: Modifier = Modifier,
    editWorkoutUiState: EditWorkoutUiState,
    showSnackbar: suspend (String) -> Unit,
    navigateToJournal: () -> Unit
) {
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    // holds onto the id of the currently viewed workout
    // so we can delete it if need be
    var workoutUpForDeletion by rememberSaveable {
        mutableStateOf("")
    }
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
            // strings needed if we want to delete a workout or set
            val onDeleteFailedSnackBarText = stringResource(
                id = R.string.error_with_workout_being_deleted,
                uiState.data.workoutDetailsModel.name
            )
            val errorWithSetsBeingDeleted = stringResource(id = R.string.error_with_sets_being_deleted)

            workoutUpForDeletion = uiState.data.id

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
                                onDeleteErrorCallback = { showSnackbar(onDeleteFailedSnackBarText) },
                                onSuccessfulDeleteCallback = {
                                    navigateToJournal()
                                }
                            )
                        )
                        showDeleteDialog = false
                    }

                )
            }
            val workoutTypeAsString =
                stringResource(id = uiState.data.workoutDetailsModel.workoutTypeEnum.stringId)
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
                    EditWorkoutBanner(workoutDate = uiState.data.date)
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
                                        weight = editWorkoutUiState.weight.toDoubleOrZero()
                                    )
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.AddNewWeightTrainingItem(
                                            newWeightLiftingItem = newWeightTrainingItem,
                                            workoutType = workoutTypeAsString,
                                            workoutModel = uiState.data
                                        )
                                    )
                                }

                                WorkoutTypeEnum.CALISTHENICS -> {}
                                WorkoutTypeEnum.CARDIO -> {}
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
                                index = index,
                                deleteSet = {
                                    editWorkoutUiState.editWorkoutEvents(
                                        EditWorkoutEvents.DeleteWorkoutSetItemInWorkoutModelList(
                                            index = index,
                                            workoutType = workoutTypeAsString,
                                            workoutModel = uiState.data,
                                            onDeleteErrorCallback = { showSnackbar(errorWithSetsBeingDeleted) }
                                        )
                                    )
                                },
                                editSet = {}
                            )
                            if (index != editWorkoutUiState.weightLiftingPropertyList.lastIndex) {
                                Spacer(modifier = Modifier.height(Spacing.spacing8))
                            }
                        }
                    }

                    WorkoutTypeEnum.CALISTHENICS -> {
                    }

                    WorkoutTypeEnum.CARDIO -> {
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
private fun WorkoutTitle(
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

@Composable
private fun WorkoutDate(workoutDate: String) {
    CommonSubtitleText(
        subtitleText = "Date: $workoutDate",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing16)
    )
}
