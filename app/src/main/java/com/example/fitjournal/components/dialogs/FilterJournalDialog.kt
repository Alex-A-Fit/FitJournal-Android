package com.example.fitjournal.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.model.enum.WorkoutTypeEnum
import com.example.fitjournal.model.ui.FilterWorkoutModel
import com.example.fitjournal.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterJournalDialog(
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    workoutList: List<FilterWorkoutModel>,
    onDismissDialog: () -> Unit,
    onConfirmDialog: () -> Unit,
    onFilterSelected: (WorkoutTypeEnum) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { onDismissDialog() },
        modifier = modifier,
        properties = properties,
        content = {
            FilterJournalSection(
                modifier = modifier,
                workoutList = workoutList,
                onDismissDialog = { onDismissDialog() },
                onConfirmDialog = { onConfirmDialog() },
                onFilterSelected = { onFilterSelected(it) }
            )
        }
    )
}

@Composable
fun FilterJournalSection(
    modifier: Modifier = Modifier,
    workoutList: List<FilterWorkoutModel>,
    onDismissDialog: () -> Unit,
    onConfirmDialog: () -> Unit,
    onFilterSelected: (WorkoutTypeEnum) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FilterJournalDialogTitle()
        FilterJournalDialogSubtitle()
        FilterJournalDialogCheckboxes(workoutList = workoutList, onFilterSelected = onFilterSelected)
        FilterJournalDialogButtons(
            modifier = Modifier.fillMaxWidth(),
            onDismissDialog = onDismissDialog,
            onConfirmDialog = onConfirmDialog
        )
    }
}

@Composable
fun FilterJournalDialogTitle() {
    Text(
        text = "Filter out your Workouts",
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun FilterJournalDialogSubtitle() {
    Text(
        text = "By Selecting an exercise type, you can view all the exercises you have done based on the type selected",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun FilterJournalDialogCheckboxes(
    workoutList: List<FilterWorkoutModel>,
    onFilterSelected: (WorkoutTypeEnum) -> Unit
) {
    workoutList.forEach { workout ->
        Checkbox(
            checked = workout.isWorkoutSelected,
            onCheckedChange = { onFilterSelected(workout.exerciseType) }
        )
        Spacer(modifier = Modifier.height(Spacing.spacing12))
    }
}

@Composable
fun FilterJournalDialogButtons(
    modifier: Modifier,
    onDismissDialog: () -> Unit,
    onConfirmDialog: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { onDismissDialog() }) {
            Text(text = "Dismiss")
        }
        TextButton(onClick = { onConfirmDialog() }) {
            Text(text = "Confirm")
        }
    }
}