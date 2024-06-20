package com.alexafit.fitjournal.home.presentation.screen.editworkout.components

import androidx.compose.runtime.Composable
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.components.EditWorkoutMandatoryWeightSection
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.home.presentation.model.events.EditWorkoutEvents
import com.alexafit.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutWeightLifting(
    editWorkoutUiState: EditWorkoutUiState
) {
    EditWorkoutSetsSection(
        isErrorVisible = editWorkoutUiState.isSetsErrorVisible,
        editSetsValue = { editWorkoutFunction: EditWorkoutFunction, setValue: String ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditSets(
                    editWorkoutFunction = editWorkoutFunction,
                    setValue = setValue
                )
            )
        },
        setsValue = editWorkoutUiState.sets,
        onSetValueChange = {
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.OnSetValueChange(
                    it
                )
            )
        }
    )
    EditWorkoutRepsSection(
        isRepsErrorVisible = editWorkoutUiState.isRepsErrorVisible,
        editRepValue = { editWorkoutFunction: EditWorkoutFunction, repValue: String ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditReps(
                    editWorkoutFunction = editWorkoutFunction,
                    repValue = repValue
                )
            )
        },
        repValue = editWorkoutUiState.reps,
        onRepValueChange = {
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.OnRepValueChange(
                    it
                )
            )
        }
    )
    EditWorkoutMandatoryWeightSection(
        isWeightErrorVisible = editWorkoutUiState.isWeightErrorVisible,
        poundsOrKilogramsText = editWorkoutUiState.weightType.stringValue,
        weightValue = editWorkoutUiState.weight,
        onWeightTypeClicked = {
            editWorkoutUiState.editWorkoutEvents(EditWorkoutEvents.EditWeightType)
        },
        onWeightValueChange = {
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.OnWeightValueChange(it)
            )
        },
        editWeightValue = { editWorkoutFunction: EditWorkoutFunction, weightValue: String ->
            editWorkoutUiState.editWorkoutEvents(
                EditWorkoutEvents.EditWeight(
                    editWorkoutFunction = editWorkoutFunction,
                    weightValue = weightValue
                )
            )
        }
    )
}
