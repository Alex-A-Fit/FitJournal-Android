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

@Composable
fun EditWorkoutRepsSection(
    isRepsErrorVisible: Boolean,
    editRepValue: (EditWorkoutFunction, String) -> Unit,
    repValue: String,
    onRepValueChange: (String) -> Unit
) {
    val isErrorVisible by remember(isRepsErrorVisible) {
        mutableStateOf(isRepsErrorVisible)
    }

    EditWorkoutPropertySection(
        workoutProperty = stringResource(id = R.string.label_reps).uppercase()
    ) {
        EditValueSmallIntegerSection(
            onSubtractValueClicked = {
                editRepValue(EditWorkoutFunction.SUBTRACT_VALUE, it)
            },
            onAddValueClicked = {
                editRepValue(EditWorkoutFunction.ADD_VALUE, it)
            },
            workoutPropertyValue = repValue,
            onValueChanged = {
                onRepValueChange(it)
            },
            doesTextFieldHaveError = isErrorVisible
        )
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_reps),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isErrorVisible) Red else Color.Transparent
    )
}
