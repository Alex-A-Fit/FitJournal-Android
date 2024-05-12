package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components

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
fun EditWorkoutDistanceSection(
    editWorkoutUiState: EditWorkoutUiState
) {
    val isDistanceErrorVisible by remember(editWorkoutUiState.isDistanceErrorVisible) {
        mutableStateOf(editWorkoutUiState.isDistanceErrorVisible)
    }
    val milesOrKilometerText by remember(editWorkoutUiState.distanceType) {
        mutableStateOf(editWorkoutUiState.distanceType)
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
                    editWorkoutUiState.editWorkoutEvents(
                        EditWorkoutEvents.EditDistance(
                            editWorkoutFunction = EditWorkoutFunction.SUBTRACT_VALUE,
                            value = it
                        )
                    )
                },
                onAddValueClicked = {
                    editWorkoutUiState.editWorkoutEvents(
                        EditWorkoutEvents.EditDistance(
                            editWorkoutFunction = EditWorkoutFunction.ADD_VALUE,
                            value = it
                        )
                    )
                },
                workoutPropertyValue = editWorkoutUiState.distance,
                onValueChanged = {
                    editWorkoutUiState.editWorkoutEvents(
                        EditWorkoutEvents.OnDistanceValueChange(distanceValue = it)
                    )
                },
                doesTextFieldHaveError = isDistanceErrorVisible
            )
            IconButton(
                onClick = {
                    editWorkoutUiState.editWorkoutEvents(
                        EditWorkoutEvents.EditDistanceType
                    )
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
    }
    ErrorText(
        text = stringResource(id = R.string.error_with_adding_distance),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        color = if (isDistanceErrorVisible) Red else Color.Transparent
    )
}
