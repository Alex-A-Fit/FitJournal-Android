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
fun EditWorkoutSetsSection(
    isErrorVisible: Boolean,
    editSetsValue: (EditWorkoutFunction, String) -> Unit,
    setsValue: String,
    onSetValueChange: (String) -> Unit
) {
    val isSetsErrorVisible by remember(isErrorVisible) {
        mutableStateOf(isErrorVisible)
    }

    EditWorkoutPropertySection(
        workoutProperty = stringResource(R.string.label_sets).uppercase()
    ) {
        EditValueSmallIntegerSection(
            onSubtractValueClicked = {
                editSetsValue(EditWorkoutFunction.SUBTRACT_VALUE, it)
            },
            onAddValueClicked = {
                editSetsValue(EditWorkoutFunction.ADD_VALUE, it)
            },
            workoutPropertyValue = setsValue,
            onValueChanged = {
                onSetValueChange(it)
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
