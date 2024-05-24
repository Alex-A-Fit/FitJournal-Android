package com.example.fitjournal.library.presentation.screen.library.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutItemDialogUiModel

@Composable
fun EditWorkoutDialog(showDialog: MutableState<Boolean>, workoutItemDialogUiModel: WorkoutItemDialogUiModel) {
    when {
        showDialog.value -> {
            EditWorkoutAlertDialog(
                onDismissRequest = { showDialog.value = false },
                onConfirmation = {
                    showDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                workoutItemDialogUiModel = workoutItemDialogUiModel
            )
        }
    }
}
