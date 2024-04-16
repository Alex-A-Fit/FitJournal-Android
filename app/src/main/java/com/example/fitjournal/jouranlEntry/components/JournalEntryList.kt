package com.example.fitjournal.jouranlEntry.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.core.data.Workouts
import com.example.fitjournal.core.domain.model.WorkoutDetail
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.components.ExerciseItem

@Composable
fun JournalEntryList(workoutList: List<WorkoutDetail>, selectedWorkout: (WorkoutDetail) -> Unit) {

    val listOfWeightLiftingWorkouts = workoutList.filter { it.workoutType == WorkoutTypeEnum.WEIGHT_TRAINING }
    val listOfCardioWorkouts = workoutList.filter { it.workoutType == WorkoutTypeEnum.CARDIO }
    val listOfCalisthenicsWorkouts = workoutList.filter { it.workoutType == WorkoutTypeEnum.CALISTHENICS }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Spacing.spacing8),
    ) {

        if (listOfWeightLiftingWorkouts.isNotEmpty()) {
            item {
                WorkoutEntry(WorkoutTypeEnum.WEIGHT_TRAINING)
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
            item {
                WorkoutEntry(WorkoutTypeEnum.CARDIO)
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
            item {
                WorkoutEntry(WorkoutTypeEnum.CALISTHENICS)
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

@Composable
fun WorkoutEntry(workoutType: WorkoutTypeEnum) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Spacing.spacing110)
            .padding(vertical = Spacing.spacing24),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(
            id = workoutType.iconImage()),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(Spacing.spacing8))
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = Spacing.spacing12, start = Spacing.spacing12)
        ) {
            Text(
                text = "${stringResource(id = workoutType.workoutTitle())}",
                color = Color.Black,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(Spacing.spacing4)
            )
        }
    }

}