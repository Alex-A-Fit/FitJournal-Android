package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components

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
fun EditWorkoutOptionalLapsSection(
    editWorkoutUiState: EditWorkoutUiState
) {
    val isLapsErrorVisible by remember(editWorkoutUiState.isLapsErrorVisible) {
        mutableStateOf(editWorkoutUiState.isLapsErrorVisible)
    }

    EditWorkoutPropertySection(
        workoutProperty = stringResource(id = R.string.label_laps_optional).uppercase()
    ) {
        EditValueDoubleSection(
            textFieldModifier = Modifier.fillMaxWidth(.5f),
            modifier = Modifier
                .fillMaxWidth(),
            onSubtractValueClicked = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.EditLaps(
                        editWorkoutFunction = EditWorkoutFunction.SUBTRACT_VALUE,
                        value = it
                    )
                )
            },
            onAddValueClicked = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.EditLaps(
                        editWorkoutFunction = EditWorkoutFunction.ADD_VALUE,
                        value = it
                    )
                )
            },
            workoutPropertyValue = editWorkoutUiState.laps,
            onValueChanged = {
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.OnLapsValueChange(lapValue = it)
                )
            },
            doesTextFieldHaveError = isLapsErrorVisible
        )
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_laps),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isLapsErrorVisible) Red else Color.Transparent
    )
}
