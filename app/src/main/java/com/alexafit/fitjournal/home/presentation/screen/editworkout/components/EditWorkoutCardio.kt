package com.alexafit.fitjournal.home.presentation.screen.editworkout.components

import androidx.compose.runtime.Composable
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutDistanceSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutMandatoryTimeSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutOptionalLapsSection
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.alexafit.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.alexafit.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutCardio(
    editWorkoutUiState: EditWorkoutUiState
) {
    EditWorkoutOptionalLapsSection(
        isLapsErrorVisible = editWorkoutUiState.isLapsErrorVisible,
        lapsValue = editWorkoutUiState.laps,
        onLapsValueChange = {
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.OnLapsValueChange(
                    it
                )
            )
        },
        editLapsEvent = { editWorkoutFunction: EditWorkoutFunction, lapValue: String ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditLaps(
                    editWorkoutFunction,
                    lapValue
                )
            )
        }
    )
    EditWorkoutDistanceSection(
        isDistanceErrorVisible = editWorkoutUiState.isDistanceErrorVisible,
        distanceType = editWorkoutUiState.distanceType,
        distanceValue = editWorkoutUiState.distance,
        editDistanceTypeEvent = {
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditDistanceType
            )
        },
        onDistanceValueChange = {
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.OnDistanceValueChange(
                    it
                )
            )
        },
        editDistanceEvent = { editWorkoutFunction: EditWorkoutFunction, distanceValue: String ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditDistance(
                    editWorkoutFunction = editWorkoutFunction,
                    value = distanceValue
                )
            )
        }

    )
    EditWorkoutMandatoryTimeSection(
        isTimeErrorVisible = editWorkoutUiState.isTimeErrorVisible,
        hourValue = editWorkoutUiState.hour,
        minuteValue = editWorkoutUiState.minute,
        secondValue = editWorkoutUiState.second,
        onTimeValueChanged = { newValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditTime(
                    value = newValue,
                    timeDeterminate = editWorkoutTimeDeterminate
                )
            )
        }
    )
}
