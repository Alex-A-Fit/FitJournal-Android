package com.example.fitjournal.library.presentation.screen.workout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R
import com.example.fitjournal.home.presentation.model.enum.WorkoutTypeEnum
import com.example.fitjournal.theme.Spacing

@Composable
fun NewWorkoutDialog(
    onDismiss: () -> Unit
) {
    // TODO Continue working on cleaning up dialog
    var workoutFieldValue by rememberSaveable {
        mutableStateOf("")
    }

    var workoutTypeChosen: WorkoutTypeEnum? by rememberSaveable {
        mutableStateOf(null)
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        modifier = Modifier
            .border(
                width = Spacing.spacing2,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(Spacing.spacing32)
            ),
        shape = RoundedCornerShape(Spacing.spacing32),
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        textContentColor = MaterialTheme.colorScheme.onBackground,
        title = { AddNewWorkoutTitle() },
        text = {
            AddNewWorkoutSubTitle(
                workoutNameValue = workoutFieldValue,
                updateWorkoutName = { workoutFieldValue = it },
                workoutTypeChosen = workoutTypeChosen,
                updateWorkoutTypeChosen = {
                    workoutTypeChosen = it
                }
            )
        },
        confirmButton = {
            Button(
                onClick = { /*TODO*/ },
                enabled = workoutTypeChosen != null
            ) {
                Text(
                    text = stringResource(id = R.string.button_create_workout),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
                    disabledContentColor = MaterialTheme.colorScheme.background
                ),
                border = BorderStroke(width = Spacing.spacing1, color = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text(
                    text = stringResource(id = R.string.button_cancel),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    )
}

@Composable
private fun AddNewWorkoutTitle() {
    Text(
        text = stringResource(id = R.string.title_create_new_workout),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun AddNewWorkoutSubTitle(
    workoutNameValue: String,
    workoutTypeChosen: WorkoutTypeEnum?,
    updateWorkoutTypeChosen: (WorkoutTypeEnum) -> Unit,
    updateWorkoutName: (String) -> Unit
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
    Column {
        Text(
            text = stringResource(id = R.string.subtitle_create_new_workout),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(Spacing.spacing16))

        OutlinedTextField(
            value = workoutNameValue,
            onValueChange = { value ->
                updateWorkoutName(value)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.label_workout_name),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.text_workout_name_placeholder),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            },
            trailingIcon = {
                if (workoutNameValue.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_cancel),
                        contentDescription = stringResource(id = R.string.content_desc_cancel_icon_clear_text),
                        modifier = Modifier.clickable { updateWorkoutName("") }
                    )
                }
            },
            textStyle = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(Spacing.spacing16))

        Text(
            text = stringResource(id = R.string.title_choose_workout_type),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(Spacing.spacing8))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
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
}
