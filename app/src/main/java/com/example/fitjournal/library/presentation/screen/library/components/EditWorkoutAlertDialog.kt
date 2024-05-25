package com.example.fitjournal.library.presentation.screen.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.DeleteButton
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.WorkoutNameTextField
import com.example.fitjournal.core.presentation.commoncomponents.text.CommonSubtitleText
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.DisabledBackgroundGray
import com.example.fitjournal.core.presentation.theme.MediumGray
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutItemDialogUiModel

typealias WorkoutName = String

@Composable
fun EditWorkoutAlertDialog(
    onDismissRequest: () -> Unit,
    onUpdateButtonClick: (WorkoutName, WorkoutTypeEnum) -> Unit,
    onAddToJournalButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    workoutItemDialogUiModel: WorkoutItemDialogUiModel
) {
    var currentWorkoutName by rememberSaveable(workoutItemDialogUiModel.libraryWorkoutItem.workoutName) {
        mutableStateOf(workoutItemDialogUiModel.libraryWorkoutItem.workoutName)
    }
    val originalWorkoutName by rememberSaveable {
        mutableStateOf(workoutItemDialogUiModel.libraryWorkoutItem.workoutName)
    }

    var workoutTypeChosen: WorkoutTypeEnum by rememberSaveable(workoutItemDialogUiModel.libraryWorkoutItem.workoutTypeEnum) {
        mutableStateOf(workoutItemDialogUiModel.libraryWorkoutItem.workoutTypeEnum)
    }
    val originalWorkoutType by rememberSaveable {
        mutableStateOf(workoutItemDialogUiModel.libraryWorkoutItem.workoutTypeEnum)
    }

    val gradient = Brush.linearGradient(
        0.0f to MaterialTheme.colorScheme.primary,
        0.7f to MaterialTheme.colorScheme.inversePrimary,
        1.0f to MaterialTheme.colorScheme.inversePrimary,
        start = Offset.Infinite,
        end = Offset.Zero
    )

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95F)
                .background(gradient, shape = RoundedCornerShape(Spacing.spacing16))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Spacing.spacing16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    text = workoutItemDialogUiModel.libraryWorkoutItem.workoutName,
                    modifier = Modifier
                        .padding(Spacing.spacing16)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(Spacing.spacing16))
                Column {
                    CommonSubtitleText(
                        stringResource(id = R.string.subtitle_update_workout),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(Spacing.spacing16))
                    WorkoutNameTextField(
                        workoutNameValue = currentWorkoutName,
                        updateWorkoutName = {
                            currentWorkoutName = it
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(Spacing.spacing32))
                    CommonSubtitleText(
                        stringResource(id = R.string.title_update_workout_type),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(Spacing.spacing8))
                    UpdateWorkoutTypeSuggestions(
                        updateWorkoutTypeChosen = {
                            workoutTypeChosen = it
                        },
                        workoutTypeChosen = workoutTypeChosen
                    )
                }
                Spacer(modifier = Modifier.weight(1F))

                // TODO Update Button Colors and Add functionality
                Column {
                    SaveButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Spacing.spacing48,
                                vertical = Spacing.spacing8
                            )
                            .border(
                                width = Spacing.spacing2,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(Spacing.spacing18)
                            ),
                        textModifier = Modifier.padding(
                            horizontal = Spacing.spacing32,
                            vertical = Spacing.spacing4
                        ),
                        text = stringResource(id = R.string.button_update_workout),
                        onClick = { onUpdateButtonClick(currentWorkoutName, workoutTypeChosen) },
                        buttonColor = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.inversePrimary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = DisabledBackgroundGray,
                            disabledContentColor = MediumGray
                        ),
                        isEnabled = !((originalWorkoutName == currentWorkoutName && originalWorkoutType == workoutTypeChosen) || currentWorkoutName.isEmpty())
                    )
                    SaveButton(
                        text = stringResource(id = R.string.button_add_to_journal),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Spacing.spacing48,
                                vertical = Spacing.spacing8
                            ),
                        textModifier = Modifier.padding(
                            horizontal = Spacing.spacing32,
                            vertical = Spacing.spacing4
                        ),
                        onClick = onAddToJournalButtonClick
                    )
                    DeleteButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Spacing.spacing48,
                                vertical = Spacing.spacing8
                            ),
                        textModifier = Modifier.padding(
                            horizontal = Spacing.spacing32,
                            vertical = Spacing.spacing4
                        ),
                        onClick = onDeleteButtonClick,
                        text = stringResource(id = R.string.button_delete_workout)
                    )
                }
                Spacer(modifier = Modifier.height(Spacing.spacing32))
            }

            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = ""
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun UpdateWorkoutTypeSuggestions(
    updateWorkoutTypeChosen: (WorkoutTypeEnum) -> Unit,
    workoutTypeChosen: WorkoutTypeEnum?
) {
    val chipColorsSelectedChip = ChipColors(
        containerColor = MaterialTheme.colorScheme.primary,
        labelColor = MaterialTheme.colorScheme.onPrimary,
        leadingIconContentColor = MaterialTheme.colorScheme.onPrimary,
        trailingIconContentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
        disabledLabelColor = MaterialTheme.colorScheme.tertiary,
        disabledLeadingIconContentColor = MaterialTheme.colorScheme.onTertiary,
        disabledTrailingIconContentColor = MaterialTheme.colorScheme.onTertiary
    )
    val chipColorsNotSelectedChip = SuggestionChipDefaults.suggestionChipColors()
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.Center
    ) {
        SuggestionChip(
            onClick = { updateWorkoutTypeChosen(WorkoutTypeEnum.CARDIO) },
            label = { Text(text = stringResource(id = R.string.title_cardio)) },
            shape = RoundedCornerShape(Spacing.spacing16),
            colors = if (workoutTypeChosen == WorkoutTypeEnum.CARDIO) chipColorsSelectedChip else chipColorsNotSelectedChip
        )
        SuggestionChip(
            onClick = { updateWorkoutTypeChosen(WorkoutTypeEnum.WEIGHT_TRAINING) },
            label = { Text(text = stringResource(id = R.string.title_weight_training)) },
            shape = RoundedCornerShape(Spacing.spacing16),
            colors = if (workoutTypeChosen == WorkoutTypeEnum.WEIGHT_TRAINING) chipColorsSelectedChip else chipColorsNotSelectedChip
        )
        SuggestionChip(
            onClick = { updateWorkoutTypeChosen(WorkoutTypeEnum.CALISTHENICS) },
            label = { Text(text = stringResource(id = R.string.title_calisthenics)) },
            shape = RoundedCornerShape(Spacing.spacing16),
            colors = if (workoutTypeChosen == WorkoutTypeEnum.CALISTHENICS) chipColorsSelectedChip else chipColorsNotSelectedChip
        )
    }
}
