package com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditValueDoubleSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutPropertySection
import com.alexafit.fitjournal.core.presentation.commoncomponents.text.ErrorText
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.presentation.theme.Red
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun EditWorkoutOptionalLapsSection(
    isLapsErrorVisible: Boolean,
    lapsValue: String,
    onLapsValueChange: (String) -> Unit,
    editLapsEvent: (EditWorkoutFunction, String) -> Unit
) {
    val isErrorVisible by remember(isLapsErrorVisible) {
        mutableStateOf(isLapsErrorVisible)
    }

    EditWorkoutPropertySection(
        workoutProperty = stringResource(id = R.string.label_laps_optional).uppercase()
    ) {
        EditValueDoubleSection(
            textFieldModifier = Modifier.fillMaxWidth(.5f),
            modifier = Modifier
                .fillMaxWidth(),
            onSubtractValueClicked = {
                editLapsEvent(
                    EditWorkoutFunction.SUBTRACT_VALUE,
                    it
                )
            },
            onAddValueClicked = {
                editLapsEvent(
                    EditWorkoutFunction.ADD_VALUE,
                    it
                )
            },
            workoutPropertyValue = lapsValue,
            onValueChanged = {
                onLapsValueChange(it)
            },
            doesTextFieldHaveError = isErrorVisible
        )
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_laps),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isErrorVisible) Red else Color.Transparent
    )
}
