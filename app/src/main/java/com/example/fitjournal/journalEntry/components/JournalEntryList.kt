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
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.core.presentation.commoncomponents.listHeader.CategoryHeader
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.journalEntry.domain.WorkoutDetail
import com.example.fitjournal.library.presentation.screen.library.components.ExerciseItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JournalEntryList(workoutList: List<WorkoutDetail>, selectedWorkout: (WorkoutDetail) -> Unit) {
    val listOfWeightLiftingWorkouts = workoutList.filter { it.workoutType == WorkoutTypeEnum.WEIGHT_TRAINING }
    val listOfCardioWorkouts = workoutList.filter { it.workoutType == WorkoutTypeEnum.CARDIO }
    val listOfCalisthenicsWorkouts = workoutList.filter { it.workoutType == WorkoutTypeEnum.CALISTHENICS }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Spacing.spacing8)
    ) {
        if (listOfWeightLiftingWorkouts.isNotEmpty()) {
            stickyHeader {
                CategoryHeader(text = stringResource(id = WorkoutTypeEnum.WEIGHT_TRAINING.workoutTitle()))
            }

            itemsIndexed(listOfWeightLiftingWorkouts) { index, exercise ->
                TextButton(onClick = { selectedWorkout.invoke(exercise) }) {
                    ExerciseItem(exercise = exercise.workoutName)
                }
                if (index != listOfWeightLiftingWorkouts.lastIndex) {
                    HorizontalDivider(
                        thickness = Spacing.spacing1,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        if (listOfCardioWorkouts.isNotEmpty()) {
            stickyHeader {
                CategoryHeader(text = stringResource(id = WorkoutTypeEnum.CARDIO.workoutTitle()))
            }

            itemsIndexed(listOfCardioWorkouts) { index, exercise ->
                TextButton(onClick = { selectedWorkout.invoke(exercise) }) {
                    ExerciseItem(exercise = exercise.workoutName)
                }
                if (index != listOfWeightLiftingWorkouts.lastIndex) {
                    HorizontalDivider(
                        thickness = Spacing.spacing1,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        if (listOfCalisthenicsWorkouts.isNotEmpty()) {
            stickyHeader {
                CategoryHeader(text = stringResource(id = WorkoutTypeEnum.CALISTHENICS.workoutTitle()))
            }

            itemsIndexed(listOfCalisthenicsWorkouts) { index, exercise ->
                TextButton(onClick = { selectedWorkout.invoke(exercise) }) {
                    ExerciseItem(exercise = exercise.workoutName)
                }
                if (index != listOfWeightLiftingWorkouts.lastIndex) {
                    HorizontalDivider(
                        thickness = Spacing.spacing1,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
