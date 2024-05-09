package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.EditWorkoutCalisthenics
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.EditWorkoutCardio
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.EditWorkoutWeightLifting
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState

@Composable
fun EditWorkoutSection(
    workoutType: WorkoutTypeEnum,
    editWorkoutUiState: EditWorkoutUiState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Spacing.spacing16,
                end = Spacing.spacing16,
                bottom = Spacing.spacing16
            )
    ) {
        when (workoutType) {
            WorkoutTypeEnum.CARDIO -> {
                EditWorkoutCardio(editWorkoutUiState = editWorkoutUiState)
            }
            WorkoutTypeEnum.CALISTHENICS -> {
                EditWorkoutCalisthenics(editWorkoutUiState = editWorkoutUiState)
            }
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                EditWorkoutWeightLifting(editWorkoutUiState = editWorkoutUiState)
            }
        }
    }
}
