package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog

@Composable
fun EditWorkoutSetProperties(
    workoutTypeDialog: WorkoutTypeDialog,
    isWorkoutValid: (Boolean, WorkoutTypeDialog?) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        when (workoutTypeDialog) {
            is WorkoutTypeDialog.Calisthenics -> {
                EditWorkoutSetCalisthenics(
                    editWorkoutSetCalisthenicsModel = workoutTypeDialog.editWorkoutSetCalisthenicsModel,
                    isWorkoutValid = isWorkoutValid
                )
            }

            is WorkoutTypeDialog.Cardio -> {
                EditWorkoutSetCardio(
                    editWorkoutSetCardioModel = workoutTypeDialog.editWorkoutSetCardioModel,
                    isWorkoutValid = isWorkoutValid
                )
            }

            is WorkoutTypeDialog.WeightLifting -> {
                EditWorkoutSetWeightLifting(
                    editWorkoutSetWeightLiftingModel = workoutTypeDialog.editWorkoutSetWeightLiftingModel,
                    isWorkoutValid = isWorkoutValid
                )
            }

            WorkoutTypeDialog.None -> Unit
        }
    }
}
