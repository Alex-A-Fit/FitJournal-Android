package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model.WorkoutTypeDialog

@Composable
fun EditWorkoutSetProperties(
    workoutTypeDialog: WorkoutTypeDialog
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        when (workoutTypeDialog) {
            is WorkoutTypeDialog.Calisthenics -> {
                EditWorkoutSetCalisthenics(workoutTypeDialog.editWorkoutSetCalisthenicsModel)
            }
            is WorkoutTypeDialog.Cardio -> {
                EditWorkoutSetCardio(editWorkoutSetCardioModel = workoutTypeDialog.editWorkoutSetCardioModel)
            }
            is WorkoutTypeDialog.WeightLifting -> {
                EditWorkoutSetWeightLifting(editWorkoutSetWeightLiftingModel = workoutTypeDialog.editWorkoutSetWeightLiftingModel)
            }
        }
    }
}
