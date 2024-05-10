package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutDistanceSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutMandatoryTimeSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutOptionalLapsSection
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutCardio(
    editWorkoutUiState: EditWorkoutUiState
) {
    EditWorkoutOptionalLapsSection(editWorkoutUiState = editWorkoutUiState)
    EditWorkoutDistanceSection(editWorkoutUiState = editWorkoutUiState)
    EditWorkoutMandatoryTimeSection(editWorkoutUiState = editWorkoutUiState)
}
