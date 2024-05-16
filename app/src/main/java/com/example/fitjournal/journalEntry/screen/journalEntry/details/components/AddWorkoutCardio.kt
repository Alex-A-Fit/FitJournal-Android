package com.example.fitjournal.journalEntry.screen.journalEntry.details.components

import androidx.compose.runtime.Composable
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutDistanceSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutMandatoryTimeSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutOptionalLapsSection
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.journalEntry.model.JournalEntryDetailsUiState
import com.example.fitjournal.journalEntry.model.events.JournalEntryDetailsEvents

@Composable
fun AddWorkoutCardio(
    journalEntryDetailsUiState: JournalEntryDetailsUiState
) {
    EditWorkoutOptionalLapsSection(
        isLapsErrorVisible = journalEntryDetailsUiState.isLapsErrorVisible,
        lapsValue = journalEntryDetailsUiState.laps,
        onLapsValueChange = {
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.OnLapsValueChange(
                    it
                )
            )
        },
        editLapsEvent = { editWorkoutFunction: EditWorkoutFunction, lapValue: String ->
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.EditLaps(
                    editWorkoutFunction,
                    lapValue
                )
            )
        }
    )
    EditWorkoutDistanceSection(
        isDistanceErrorVisible = journalEntryDetailsUiState.isDistanceErrorVisible,
        distanceType = journalEntryDetailsUiState.distanceType,
        distanceValue = journalEntryDetailsUiState.distance,
        editDistanceTypeEvent = {
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.EditDistanceType
            )
        },
        onDistanceValueChange = {
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.OnDistanceValueChange(
                    it
                )
            )
        },
        editDistanceEvent = { editWorkoutFunction: EditWorkoutFunction, distanceValue: String ->
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.EditDistance(
                    editWorkoutFunction = editWorkoutFunction,
                    value = distanceValue
                )
            )
        }

    )
    EditWorkoutMandatoryTimeSection(
        isTimeErrorVisible = journalEntryDetailsUiState.isTimeErrorVisible,
        hourValue = journalEntryDetailsUiState.hour,
        minuteValue = journalEntryDetailsUiState.minute,
        secondValue = journalEntryDetailsUiState.second,
        onTimeValueChanged = { newValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            journalEntryDetailsUiState.journalEntryDetailsEvents(
                JournalEntryDetailsEvents.EditTime(
                    value = newValue,
                    timeDeterminate = editWorkoutTimeDeterminate
                )
            )
        }
    )
}
