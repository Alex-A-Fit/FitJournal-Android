package com.example.fitjournal.journalEntry.screen.journalEntry.details.components

import androidx.compose.runtime.Composable
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.components.EditWorkoutMandatoryWeightSection
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.journalEntry.model.AddWorkoutDetailUiState
import com.example.fitjournal.journalEntry.model.events.AddWorkoutDetailEvents

@Composable
fun AddWorkoutWeightLifting(
    addWorkoutDetailUiState: AddWorkoutDetailUiState
) {
    EditWorkoutSetsSection(
        isErrorVisible = addWorkoutDetailUiState.isSetsErrorVisible,
        editSetsValue = { editWorkoutFunction: EditWorkoutFunction, setValue: String ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditSets(
                    editWorkoutFunction = editWorkoutFunction,
                    setValue = setValue
                )
            )
        },
        setsValue = addWorkoutDetailUiState.sets,
        onSetValueChange = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.OnSetValueChange(
                    it
                )
            )
        }
    )
    EditWorkoutRepsSection(
        isRepsErrorVisible = addWorkoutDetailUiState.isRepsErrorVisible,
        editRepValue = { editWorkoutFunction: EditWorkoutFunction, repValue: String ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditReps(
                    editWorkoutFunction = editWorkoutFunction,
                    repValue = repValue
                )
            )
        },
        repValue = addWorkoutDetailUiState.reps,
        onRepValueChange = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.OnRepValueChange(
                    it
                )
            )
        }
    )
    EditWorkoutMandatoryWeightSection(
        isWeightErrorVisible = addWorkoutDetailUiState.isWeightErrorVisible,
        poundsOrKilogramsText = addWorkoutDetailUiState.weightType.stringValue,
        weightValue = addWorkoutDetailUiState.weight,
        onWeightTypeClicked = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(AddWorkoutDetailEvents.EditWeightType)
        },
        onWeightValueChange = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.OnWeightValueChange(it)
            )
        },
        editWeightValue = { editWorkoutFunction: EditWorkoutFunction, weightValue: String ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditWeight(
                    editWorkoutFunction = editWorkoutFunction,
                    weightValue = weightValue
                )
            )
        }
    )
}
