package com.example.fitjournal.journalEntry.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.core.presentation.commoncomponents.listHeader.CategoryHeader
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.components.ExerciseItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JournalEntryList(
    workoutList: List<Pair<WorkoutTypeEnum, List<RealmWorkoutLibrary>>>,
    selectedWorkout: (RealmWorkoutLibrary) -> Unit
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
                    CategoryHeader(text = workoutType.workoutTitle())
                }

                itemsIndexed(workouts) { index, exercise ->
                    TextButton(onClick = { selectedWorkout.invoke(exercise) }) {
                        ExerciseItem(exercise = exercise.name)
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
