package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents

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
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutPropertySection
import com.example.fitjournal.core.presentation.commoncomponents.text.ErrorText
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.theme.Red
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutRepsSection(
    editWorkoutUiState: EditWorkoutUiState
) {
    val isRepsErrorVisible by remember(editWorkoutUiState.isRepsErrorVisible) {
        mutableStateOf(editWorkoutUiState.isRepsErrorVisible)
    }

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
}
