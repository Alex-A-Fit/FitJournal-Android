package com.example.fitjournal.addWorkout.screen.journalEntry.details.components

import androidx.compose.runtime.Composable
import com.example.fitjournal.addWorkout.model.AddWorkoutDetailUiState
import com.example.fitjournal.addWorkout.model.events.AddWorkoutDetailEvents
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalTimeSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalWeightSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate

@Composable
fun AddWorkoutCalisthenics(
    addWorkoutDetailUiState: AddWorkoutDetailUiState
) {
    EditWorkoutSetsSection(
        isErrorVisible = addWorkoutDetailUiState.isSetsErrorVisible,
        editSetsValue = { editWorkoutFunction: EditWorkoutFunction, setValue: String ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditSets(
                    editWorkoutFunction,
                    setValue
                )
            )
        },
        setsValue = addWorkoutDetailUiState.sets,
        onSetValueChange = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(AddWorkoutDetailEvents.OnSetValueChange(it))
        }
    )
    EditWorkoutRepsSection(
        isRepsErrorVisible = addWorkoutDetailUiState.isRepsErrorVisible,
        editRepValue = { editWorkoutFunction: EditWorkoutFunction, repValue: String ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditReps(
                    editWorkoutFunction,
                    repValue
                )
            )
        },
        repValue = addWorkoutDetailUiState.reps,
        onRepValueChange = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(AddWorkoutDetailEvents.OnRepValueChange(it))
        }
    )
    EditWorkoutOptionalWeightSection(
        isWeightErrorVisible = addWorkoutDetailUiState.isWeightErrorVisible,
        poundsOrKilogramsText = addWorkoutDetailUiState.weightType.stringValue,
        weightValue = addWorkoutDetailUiState.weight,
        onWeightTypeClicked = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(AddWorkoutDetailEvents.EditWeightType)
        },
        onWeightValueChange = {
            addWorkoutDetailUiState.addWorkoutDetailEvents(AddWorkoutDetailEvents.OnWeightValueChange(it))
        },
        editWeightValue = { editWorkoutFunction: EditWorkoutFunction, weightValue: String ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditWeight(
                    editWorkoutFunction,
                    weightValue
                )
            )
        }
    )
    EditWorkoutOptionalTimeSection(
        isTimeErrorVisible = addWorkoutDetailUiState.isTimeErrorVisible,
        hourValue = addWorkoutDetailUiState.hour,
        minuteValue = addWorkoutDetailUiState.minute,
        secondValue = addWorkoutDetailUiState.second,
        onTimeValueChanged = { timeValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            addWorkoutDetailUiState.addWorkoutDetailEvents(
                AddWorkoutDetailEvents.EditTime(
                    timeValue,
                    editWorkoutTimeDeterminate
                )
            )
        }
    )
}
