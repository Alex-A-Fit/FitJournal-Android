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
fun EditWorkoutSetsSection(
    editWorkoutUiState: EditWorkoutUiState
) {
    val isSetsErrorVisible by remember(editWorkoutUiState.isSetsErrorVisible) {
        mutableStateOf(editWorkoutUiState.isSetsErrorVisible)
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
}
