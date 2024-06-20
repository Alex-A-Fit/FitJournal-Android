package com.alexafit.fitjournal.addWorkout.screen.addworkout.details.components

import androidx.compose.runtime.Composable
import com.alexafit.fitjournal.addWorkout.model.AddWorkoutDetailUiState
import com.alexafit.fitjournal.addWorkout.model.events.AddWorkoutDetailEvents
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutDistanceSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutMandatoryTimeSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutOptionalLapsSection
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate

@Composable
fun AddWorkoutCardio(
    addWorkoutDetailUiState: AddWorkoutDetailUiState
) {
    EditWorkoutOptionalLapsSection(
        isLapsErrorVisible = addWorkoutDetailUiState.isLapsErrorVisible,
        lapsValue = addWorkoutDetailUiState.laps,
        onLapsValueChange = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.OnLapsValueChange(
                    it
                )
            )
        },
        editLapsEvent = { editWorkoutFunction: EditWorkoutFunction, lapValue: String ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditLaps(
                    editWorkoutFunction,
                    lapValue
                )
            )
        }
    )
    EditWorkoutDistanceSection(
        isDistanceErrorVisible = addWorkoutDetailUiState.isDistanceErrorVisible,
        distanceType = addWorkoutDetailUiState.distanceType,
        distanceValue = addWorkoutDetailUiState.distance,
        editDistanceTypeEvent = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditDistanceType
            )
        },
        onDistanceValueChange = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.OnDistanceValueChange(
                    it
                )
            )
        },
        editDistanceEvent = { editWorkoutFunction: EditWorkoutFunction, distanceValue: String ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditDistance(
                    editWorkoutFunction = editWorkoutFunction,
                    value = distanceValue
                )
            )
        }

    )
    EditWorkoutMandatoryTimeSection(
        isTimeErrorVisible = addWorkoutDetailUiState.isTimeErrorVisible,
        hourValue = addWorkoutDetailUiState.hour,
        minuteValue = addWorkoutDetailUiState.minute,
        secondValue = addWorkoutDetailUiState.second,
        onTimeValueChanged = { newValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditTime(
                    value = newValue,
                    timeDeterminate = editWorkoutTimeDeterminate
                )
            )
        }
    )
}
