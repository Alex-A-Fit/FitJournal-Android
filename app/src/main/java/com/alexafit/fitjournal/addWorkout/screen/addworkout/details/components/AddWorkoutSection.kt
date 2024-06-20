package com.alexafit.fitjournal.addWorkout.screen.addworkout.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexafit.fitjournal.addWorkout.model.AddWorkoutDetailUiState
import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun AddWorkoutSection(
    workoutType: WorkoutTypeEnum,
    addWorkoutDetailUiState: AddWorkoutDetailUiState
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
                AddWorkoutCardio(addWorkoutDetailUiState = addWorkoutDetailUiState)
            }
            WorkoutTypeEnum.CALISTHENICS -> {
                AddWorkoutCalisthenics(addWorkoutDetailUiState = addWorkoutDetailUiState)
            }
            WorkoutTypeEnum.WEIGHT_TRAINING -> {
                AddWorkoutWeightLifting(addWorkoutDetailUiState = addWorkoutDetailUiState)
            }
        }
    }
}
