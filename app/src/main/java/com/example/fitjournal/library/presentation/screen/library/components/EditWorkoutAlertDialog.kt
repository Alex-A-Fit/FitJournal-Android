package com.example.fitjournal.library.presentation.screen.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.CreateWorkoutSubtitle
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.WorkoutNameTextField
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.WorkoutTypeSubtitle
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.WorkoutTypeSuggestions
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun EditWorkoutAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    val gradient = Brush.linearGradient(
        0.0f to MaterialTheme.colorScheme.primary,
        1.0f to Color.White,
        start = Offset.Infinite,
        end = Offset.Zero
    )

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.95F)
                .background(gradient, shape = RoundedCornerShape(Spacing.spacing16))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Spacing.spacing16)
            ) {
                Text(
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    text = dialogTitle,
                    modifier = Modifier
                        .padding(Spacing.spacing16)
                        .align(alignment = Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(Spacing.spacing16))
                Column {
                    // TODO Change 'Workout Name' text
                    CreateWorkoutSubtitle()

                    Spacer(modifier = Modifier.height(Spacing.spacing16))

                    // TODO Make TextField work
                    WorkoutNameTextField(
                        workoutNameValue = dialogTitle,
                        updateWorkoutName = {}
                    )

                    Spacer(modifier = Modifier.height(Spacing.spacing16))

                    WorkoutTypeSubtitle()

                    Spacer(modifier = Modifier.height(Spacing.spacing8))

                    // TODO add functionality
                    WorkoutTypeSuggestions(
                        updateWorkoutTypeChosen = {},
                        workoutTypeChosen = WorkoutTypeEnum.WEIGHT_TRAINING
                    )
                }

                Spacer(modifier = Modifier.weight(1F))

                // TODO Update Button Colors and Add functionality
                Column {
                    ElevatedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onDismissRequest
                    ) {
                        Text(text = "Update Workout")
                    }
                    ElevatedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onDismissRequest
                    ) {
                        Text(text = "Delete Workout")
                    }
                    ElevatedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onDismissRequest
                    ) {
                        Text(text = "Add Workout")
                    }
                }
                Spacer(modifier = Modifier.height(Spacing.spacing32))
            }

            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = ""
                )
            }
        }
    }
}
