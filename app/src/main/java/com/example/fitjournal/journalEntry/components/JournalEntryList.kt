package com.example.fitjournal.journalEntry.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.listHeader.CategoryHeader
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JournalEntryList(
    workoutList: SnapshotStateList<WorkoutCategory>,
    selectedWorkout: (String, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Spacing.spacing8)
    ) {
        workoutList.forEach { childList ->
            val (workoutType, workouts) = childList
            if (workouts.isNotEmpty()) {
                stickyHeader {
                    CategoryHeader(text = workoutType)
                }

                itemsIndexed(workouts) { index, exercise ->
                    TextButton(
                        onClick = { selectedWorkout(exercise.workoutName, workoutType) }
                    ) {
                        ExerciseItem(exercise = exercise.workoutName)
                    }
                    if (index != workouts.lastIndex) {
                        HorizontalDivider(
                            thickness = Spacing.spacing1,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ExerciseItem(exercise: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing12),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ExerciseName(exercise = exercise)
        Image(
            painter = painterResource(id = R.drawable.ic_right_chevron),
            contentDescription = stringResource(id = R.string.content_desc_navigate_to_add_workout_icon)
        )
    }
}

@Composable
private fun ExerciseName(exercise: String) {
    Text(
        text = exercise,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyLarge
    )
}
