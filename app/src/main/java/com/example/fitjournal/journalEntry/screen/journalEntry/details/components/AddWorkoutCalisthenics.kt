package com.example.fitjournal.journalEntry.screen.journalEntry.details.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalTimeSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalWeightSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.journalEntry.model.JournalEntryDetailsUiState
import com.example.fitjournal.journalEntry.model.events.JournalEntryDetailsEvents

@Composable
fun AddWorkoutCalisthenics(
    journalEntryDetailsUiState: JournalEntryDetailsUiState
) {
    EditWorkoutSetsSection(
        isErrorVisible = journalEntryDetailsUiState.isSetsErrorVisible,
        editSetsValue = { editWorkoutFunction: EditWorkoutFunction, setValue: String ->
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.EditSets(
                    editWorkoutFunction,
                    setValue
                )
            )
        },
        setsValue = journalEntryDetailsUiState.sets,
        onSetValueChange = {
            journalEntryDetailsUiState.journalEntryDetailsEvents(JournalEntryDetailsEvents.OnSetValueChange(it))
        }
    )
    EditWorkoutRepsSection(
        isRepsErrorVisible = journalEntryDetailsUiState.isRepsErrorVisible,
        editRepValue = { editWorkoutFunction: EditWorkoutFunction, repValue: String ->
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.EditReps(
                    editWorkoutFunction,
                    repValue
                )
            )
        },
        repValue = journalEntryDetailsUiState.reps,
        onRepValueChange = {
            journalEntryDetailsUiState.journalEntryDetailsEvents(JournalEntryDetailsEvents.OnRepValueChange(it))
        }
    )
    EditWorkoutOptionalWeightSection(
        isWeightErrorVisible = journalEntryDetailsUiState.isWeightErrorVisible,
        poundsOrKilogramsText = journalEntryDetailsUiState.weightType.stringValue,
        weightValue = journalEntryDetailsUiState.weight,
        onWeightTypeClicked = {
            journalEntryDetailsUiState.journalEntryDetailsEvents(JournalEntryDetailsEvents.EditWeightType)
        },
        onWeightValueChange = {
            journalEntryDetailsUiState.journalEntryDetailsEvents(JournalEntryDetailsEvents.OnWeightValueChange(it))
        },
        editWeightValue = { editWorkoutFunction: EditWorkoutFunction, weightValue: String ->
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.EditWeight(
                    editWorkoutFunction,
                    weightValue
                )
            )
        }
    )
    EditWorkoutOptionalTimeSection(
        isTimeErrorVisible = journalEntryDetailsUiState.isTimeErrorVisible,
        hourValue = journalEntryDetailsUiState.hour,
        minuteValue = journalEntryDetailsUiState.minute,
        secondValue = journalEntryDetailsUiState.second,
        onTimeValueChanged = { timeValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.EditTime(
                    timeValue,
                    editWorkoutTimeDeterminate
                )
            )
        }
    )
}
