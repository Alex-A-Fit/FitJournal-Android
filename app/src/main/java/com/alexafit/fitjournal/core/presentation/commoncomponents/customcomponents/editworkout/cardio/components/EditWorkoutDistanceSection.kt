package com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components

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
import com.alexafit.fitjournal.home.presentation.model.enum.CardioDistanceType

@Composable
fun EditWorkoutDistanceSection(
    isDistanceErrorVisible: Boolean,
    distanceType: CardioDistanceType,
    distanceValue: String,
    editDistanceTypeEvent: () -> Unit,
    onDistanceValueChange: (String) -> Unit,
    editDistanceEvent: (EditWorkoutFunction, String) -> Unit
) {
    val isErrorVisible by remember(isDistanceErrorVisible) {
        mutableStateOf(isDistanceErrorVisible)
    }
    val milesOrKilometerText by remember(distanceType) {
        mutableStateOf(distanceType)
    }
    EditWorkoutPropertySection(
        workoutProperty = stringResource(
            id = R.string.label_distance_with_value,
            milesOrKilometerText.stringValue
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
                    editDistanceEvent(
                        EditWorkoutFunction.SUBTRACT_VALUE,
                        it
                    )
                },
                onAddValueClicked = {
                    editDistanceEvent(
                        EditWorkoutFunction.ADD_VALUE,
                        it
                    )
                },
                workoutPropertyValue = distanceValue,
                onValueChanged = {
                    onDistanceValueChange(it)
                },
                doesTextFieldHaveError = isErrorVisible
            )
            IconButton(
                onClick = editDistanceTypeEvent,
                modifier = Modifier.weight(0.5f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_refresh),
                    contentDescription = stringResource(id = R.string.content_desc_distance_type_switch_icon),
                    modifier = Modifier.size(Spacing.spacing48)
                )
            }
        }
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_distance),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isErrorVisible) Red else Color.Transparent
    )
}
