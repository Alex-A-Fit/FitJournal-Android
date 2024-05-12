package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalTimeSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalWeightSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutCalisthenics(
    editWorkoutUiState: EditWorkoutUiState
) {
    EditWorkoutSetsSection(editWorkoutUiState = editWorkoutUiState)
    EditWorkoutRepsSection(editWorkoutUiState = editWorkoutUiState)
    EditWorkoutOptionalWeightSection(editWorkoutUiState = editWorkoutUiState)
    EditWorkoutOptionalTimeSection(editWorkoutUiState = editWorkoutUiState)
}
