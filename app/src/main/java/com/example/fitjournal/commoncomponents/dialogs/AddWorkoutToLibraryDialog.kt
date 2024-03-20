package com.example.fitjournal.commoncomponents.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R
import com.example.fitjournal.home.presentation.model.enum.WorkoutTypeEnum
import com.example.fitjournal.theme.Spacing

@Composable
fun AddWorkoutToLibraryDialog(
    dismissDialog: () -> Unit,
    addNewWorkoutToLibrary: (String, WorkoutTypeEnum) -> Unit
) {
    var workoutName by rememberSaveable {
        mutableStateOf("")
    }

    var workoutTypeChosen: WorkoutTypeEnum? by rememberSaveable {
        mutableStateOf(null)
    }

    AlertDialog(
        onDismissRequest = { dismissDialog() },
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
                workoutNameValue = workoutName,
                updateWorkoutName = {
                    workoutName = it
                },
                workoutTypeChosen = workoutTypeChosen,
                updateWorkoutTypeChosen = {
                    workoutTypeChosen = it
                }
            )
        },
        confirmButton = {
            AddWorkoutToLibraryBtn(
                workoutTypeChosen = workoutTypeChosen,
                workoutName = workoutName,
                addNewWorkoutToLibrary = addNewWorkoutToLibrary
            )
        },
        dismissButton = {
            DismissDialogBtn(
                dismissDialog = dismissDialog
            )
        }
    )
}

@Composable
private fun DismissDialogBtn(
    dismissDialog: () -> Unit
) {
    Button(
        onClick = { dismissDialog() },
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
            disabledContentColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(
            width = Spacing.spacing1,
            color = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(
            text = stringResource(id = R.string.button_cancel),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun AddWorkoutToLibraryBtn(
    workoutTypeChosen: WorkoutTypeEnum?,
    workoutName: String,
    addNewWorkoutToLibrary: (String, WorkoutTypeEnum) -> Unit
) {
    Button(
        onClick = {
            workoutTypeChosen?.let { workoutType ->
                addNewWorkoutToLibrary(
                    workoutName,
                    workoutType
                )
            }
        },
        enabled = (workoutTypeChosen != null && workoutName.isNotEmpty())
    ) {
        Text(
            text = stringResource(id = R.string.button_create_workout),
            style = MaterialTheme.typography.bodyMedium
        )
    }
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

    Column {
        CreateWorkoutSubtitle()

        Spacer(modifier = Modifier.height(Spacing.spacing16))

        WorkoutNameTextField(
            workoutNameValue = workoutNameValue,
            updateWorkoutName = updateWorkoutName
        )

        Spacer(modifier = Modifier.height(Spacing.spacing16))

        WorkoutTypeSubtitle()

        Spacer(modifier = Modifier.height(Spacing.spacing8))

        WorkoutTypeSuggestions(
            updateWorkoutTypeChosen = updateWorkoutTypeChosen,
            workoutTypeChosen = workoutTypeChosen
        )
    }
}

@Composable
fun CreateWorkoutSubtitle() {
    Text(
        text = stringResource(id = R.string.subtitle_create_new_workout),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
}

@Composable
fun WorkoutNameTextField(
    workoutNameValue: String,
    updateWorkoutName: (String) -> Unit
) {
    val localKeyboard = LocalSoftwareKeyboardController.current

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
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(
            onDone = { localKeyboard?.hide() }
        ),
        singleLine = true
    )
}

@Composable
fun WorkoutTypeSubtitle() {
    Text(
        text = stringResource(id = R.string.title_choose_workout_type),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
}

@Composable
private fun WorkoutTypeSuggestions(
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