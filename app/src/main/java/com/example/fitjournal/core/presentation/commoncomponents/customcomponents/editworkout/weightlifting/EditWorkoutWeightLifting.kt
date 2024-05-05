package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditValueSmallIntegerSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditValueWeightSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutPropertySection
import com.example.fitjournal.core.presentation.commoncomponents.text.ErrorText
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.theme.Red
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutWeightLifting(
    editWorkoutUiState: EditWorkoutUiState
) {
    val isRepsErrorVisible by remember(editWorkoutUiState.isRepsErrorVisible) {
        mutableStateOf(editWorkoutUiState.isRepsErrorVisible)
    }
    val isSetsErrorVisible by remember(editWorkoutUiState.isSetsErrorVisible) {
        mutableStateOf(editWorkoutUiState.isSetsErrorVisible)
    }
    val isWeightErrorVisible by remember(editWorkoutUiState.isWeightErrorVisible) {
        mutableStateOf(editWorkoutUiState.isWeightErrorVisible)
    }

    EditWorkoutPropertySection(
        workoutProperty = stringResource(R.string.label_sets).uppercase()
    ) {
        EditValueSmallIntegerSection(
            onSubtractValueClicked = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.EditSets(
                        editWorkoutFunction = EditWorkoutFunction.SUBTRACT_VALUE,
                        setValue = it
                    )
                )
            },
            onAddValueClicked = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.EditSets(
                        editWorkoutFunction = EditWorkoutFunction.ADD_VALUE,
                        setValue = it
                    )
                )
            },
            workoutPropertyValue = editWorkoutUiState.sets,
            onValueChanged = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.OnSetValueChange(setValue = it)
                )
            },
            doesTextFieldHaveError = isSetsErrorVisible
        )
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_sets),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isSetsErrorVisible) Red else Color.Transparent
    )
    EditWorkoutPropertySection(
        workoutProperty = stringResource(id = R.string.label_reps).uppercase()
    ) {
        EditValueSmallIntegerSection(
            onSubtractValueClicked = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.EditReps(
                        editWorkoutFunction = EditWorkoutFunction.SUBTRACT_VALUE,
                        repValue = it
                    )
                )
            },
            onAddValueClicked = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.EditReps(
                        editWorkoutFunction = EditWorkoutFunction.ADD_VALUE,
                        repValue = it
                    )
                )
            },
            workoutPropertyValue = editWorkoutUiState.reps,
            onValueChanged = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.OnRepValueChange(repValue = it)
                )
            },
            doesTextFieldHaveError = isRepsErrorVisible
        )
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_reps),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isRepsErrorVisible) Red else Color.Transparent
    )
    EditWorkoutPropertySection(
        workoutProperty = stringResource(id = R.string.label_weight).uppercase()
    ) {
        EditValueWeightSection(
            onSubtractValueClicked = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.EditWeight(
                        editWorkoutFunction = EditWorkoutFunction.SUBTRACT_VALUE,
                        weightValue = it,
                        valueDifferential = 1.0
                    )
                )
            },
            onAddValueClicked = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.EditWeight(
                        editWorkoutFunction = EditWorkoutFunction.ADD_VALUE,
                        weightValue = it,
                        valueDifferential = 1.0
                    )
                )
            },
            workoutPropertyValue = editWorkoutUiState.weight,
            onValueChanged = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.OnWeightValueChange(weightValue = it)
                )
            },
            doesTextFieldHaveError = isWeightErrorVisible
        )
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_weight),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isWeightErrorVisible) Red else Color.Transparent
    )
}
