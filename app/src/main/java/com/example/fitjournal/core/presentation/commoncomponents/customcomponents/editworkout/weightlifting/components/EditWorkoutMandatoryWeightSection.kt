package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.components

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
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditValueDoubleSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutPropertySection
import com.example.fitjournal.core.presentation.commoncomponents.text.ErrorText
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.theme.Red
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutMandatoryWeightSection(
    editWorkoutUiState: EditWorkoutUiState
) {
    val isWeightErrorVisible by remember(editWorkoutUiState.isWeightErrorVisible) {
        mutableStateOf(editWorkoutUiState.isWeightErrorVisible)
    }

    EditWorkoutPropertySection(
        workoutProperty = stringResource(id = R.string.label_weight).uppercase()
    ) {
        EditValueDoubleSection(
            textFieldModifier = Modifier.fillMaxWidth(.5f),
            modifier = Modifier.fillMaxWidth(),
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
