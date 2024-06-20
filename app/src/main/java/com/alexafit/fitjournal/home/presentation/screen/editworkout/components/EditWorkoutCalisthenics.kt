package com.alexafit.fitjournal.home.presentation.screen.editworkout.components

import androidx.compose.runtime.Composable
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalTimeSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalWeightSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.alexafit.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.alexafit.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutCalisthenics(
    editWorkoutUiState: EditWorkoutUiState
) {
    EditWorkoutSetsSection(
        isErrorVisible = editWorkoutUiState.isSetsErrorVisible,
        editSetsValue = { editWorkoutFunction: EditWorkoutFunction, setValue: String ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditSets(
                    editWorkoutFunction,
                    setValue
                )
            )
        },
        setsValue = editWorkoutUiState.sets,
        onSetValueChange = {
            editWorkoutUiState.editWorkoutEvents(EditWorkoutEvents.OnSetValueChange(it))
        }
    )
    EditWorkoutRepsSection(
        isRepsErrorVisible = editWorkoutUiState.isRepsErrorVisible,
        editRepValue = { editWorkoutFunction: EditWorkoutFunction, repValue: String ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditReps(
                    editWorkoutFunction,
                    repValue
                )
            )
        },
        repValue = editWorkoutUiState.reps,
        onRepValueChange = {
            editWorkoutUiState.editWorkoutEvents(EditWorkoutEvents.OnRepValueChange(it))
        }
    )
    EditWorkoutOptionalWeightSection(
        isWeightErrorVisible = editWorkoutUiState.isWeightErrorVisible,
        poundsOrKilogramsText = editWorkoutUiState.weightType.stringValue,
        weightValue = editWorkoutUiState.weight,
        onWeightTypeClicked = {
            editWorkoutUiState.editWorkoutEvents(EditWorkoutEvents.EditWeightType)
        },
        onWeightValueChange = {
            editWorkoutUiState.editWorkoutEvents(EditWorkoutEvents.OnWeightValueChange(it))
        },
        editWeightValue = { editWorkoutFunction: EditWorkoutFunction, weightValue: String ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditWeight(
                    editWorkoutFunction,
                    weightValue
                )
            )
        }
    )
    EditWorkoutOptionalTimeSection(
        isTimeErrorVisible = editWorkoutUiState.isTimeErrorVisible,
        hourValue = editWorkoutUiState.hour,
        minuteValue = editWorkoutUiState.minute,
        secondValue = editWorkoutUiState.second,
        onTimeValueChanged = { timeValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditTime(
                    timeValue,
                    editWorkoutTimeDeterminate
                )
            )
        }
    )
}
