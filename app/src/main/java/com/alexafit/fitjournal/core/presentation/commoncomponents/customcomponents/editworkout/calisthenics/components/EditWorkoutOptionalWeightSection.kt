package com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditValueDoubleSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.EditWorkoutPropertySection
import com.alexafit.fitjournal.core.presentation.commoncomponents.text.ErrorText
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.presentation.theme.Red
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun EditWorkoutOptionalWeightSection(
    isWeightErrorVisible: Boolean,
    poundsOrKilogramsText: String,
    weightValue: String,
    onWeightTypeClicked: () -> Unit,
    onWeightValueChange: (String) -> Unit,
    editWeightValue: (EditWorkoutFunction, String) -> Unit
) {
    val isErrorVisible by remember(isWeightErrorVisible) {
        mutableStateOf(isWeightErrorVisible)
    }
    val weightTypeText by remember(poundsOrKilogramsText) {
        mutableStateOf(poundsOrKilogramsText)
    }
    EditWorkoutPropertySection(
        workoutProperty = stringResource(
            id = R.string.label_weight_optional,
            weightTypeText
        ).uppercase()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EditValueDoubleSection(
                textFieldModifier = Modifier.fillMaxWidth(.6f),
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                onSubtractValueClicked = {
                    editWeightValue(EditWorkoutFunction.SUBTRACT_VALUE, it)
                },
                onAddValueClicked = {
                    editWeightValue(EditWorkoutFunction.ADD_VALUE, it)
                },
                workoutPropertyValue = weightValue,
                onValueChanged = {
                    onWeightValueChange(it)
                },
                doesTextFieldHaveError = isErrorVisible
            )
            IconButton(
                onClick = {
                    onWeightTypeClicked()
                },
                modifier = Modifier.weight(0.5f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_refresh),
                    contentDescription = stringResource(id = R.string.content_desc_distance_type_switch_icon),
                    modifier = Modifier.size(Spacing.spacing48)
                )
            }
        }
        ErrorText(
            text = stringResource(id = R.string.error_with_adding_weight),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.spacing8),
            color = if (isErrorVisible) Red else Color.Transparent
        )
    }
}
