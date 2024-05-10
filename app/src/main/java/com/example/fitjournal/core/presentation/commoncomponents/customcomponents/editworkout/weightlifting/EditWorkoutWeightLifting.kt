package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.components.EditWorkoutMandatoryWeightSection
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutWeightLifting(
    editWorkoutUiState: EditWorkoutUiState
) {
    EditWorkoutSetsSection(editWorkoutUiState = editWorkoutUiState)
    EditWorkoutRepsSection(editWorkoutUiState = editWorkoutUiState)
    EditWorkoutMandatoryWeightSection(editWorkoutUiState = editWorkoutUiState)
}
