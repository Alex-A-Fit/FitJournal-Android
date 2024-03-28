package com.example.fitjournal.statistics.presentation.section

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.library.presentation.screen.library.components.ExerciseItem
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutItem
import com.example.fitjournal.theme.Spacing

@Composable
fun WorkoutListSection(
    modifier: Modifier = Modifier,
    listOfSearchedWorkouts: List<LibraryWorkoutItem>
) {
    LazyColumn {
        itemsIndexed(listOfSearchedWorkouts) { index, exercise ->
            ExerciseItem(exercise = exercise.workoutName)
            if (index != listOfSearchedWorkouts.lastIndex) {
                HorizontalDivider(
                    thickness = Spacing.spacing1,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}