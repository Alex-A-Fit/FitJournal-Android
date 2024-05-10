package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components

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
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditValueTime
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutPropertySection
import com.example.fitjournal.core.presentation.commoncomponents.text.ErrorText
import com.example.fitjournal.core.presentation.theme.Red
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutOptionalTimeSection(
    editWorkoutUiState: EditWorkoutUiState
) {
    val isTimeErrorVisible by remember(editWorkoutUiState.isTimeErrorVisible) {
        mutableStateOf(editWorkoutUiState.isTimeErrorVisible)
    }

    EditWorkoutPropertySection(
        workoutProperty = stringResource(id = R.string.label_time_elapsed_optional).uppercase()
    ) {
        EditValueTime(
            onValueChanged = { value, timeDeterminate ->
                editWorkoutUiState.editWorkoutEvents(
                    EditWorkoutEvents.EditTime(
                        value = value,
                        timeDeterminate = timeDeterminate
                    )
                )
            },
            hrValue = editWorkoutUiState.hour,
            minValue = editWorkoutUiState.minute,
            secValue = editWorkoutUiState.second,
            doesTextFieldHaveError = isTimeErrorVisible
        )
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_time),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isTimeErrorVisible) Red else Color.Transparent
    )
}
