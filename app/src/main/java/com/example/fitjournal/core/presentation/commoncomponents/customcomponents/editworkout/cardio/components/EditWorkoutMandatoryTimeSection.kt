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
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditValueTime
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutPropertySection
import com.example.fitjournal.core.presentation.commoncomponents.text.ErrorText
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.presentation.theme.Red
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun EditWorkoutMandatoryTimeSection(
    isTimeErrorVisible: Boolean,
    hourValue: String,
    minuteValue: String,
    secondValue: String,
    onTimeValueChanged: (String, EditWorkoutTimeDeterminate) -> Unit
) {
    val isErrorVisible by remember(isTimeErrorVisible) {
        mutableStateOf(isTimeErrorVisible)
    }
    EditWorkoutPropertySection(
        workoutProperty = stringResource(id = R.string.label_time_elapsed).uppercase()
    ) {
        EditValueTime(
            onValueChanged = { value, timeDeterminate ->
                onTimeValueChanged(value, timeDeterminate)
            },
            hrValue = hourValue,
            minValue = minuteValue,
            secValue = secondValue,
            doesTextFieldHaveError = isErrorVisible
        )
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_time),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isErrorVisible) Red else Color.Transparent
    )
}
