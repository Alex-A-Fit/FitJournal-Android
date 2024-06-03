package com.example.fitjournal.statistics.presentation.components.uistate

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.listHeader.CategoryHeader
import com.example.fitjournal.core.presentation.commoncomponents.textField.SearchBar
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategoryByJournal
import com.example.fitjournal.statistics.presentation.model.StatisticsEvents

typealias WorkoutName = String

@Composable
fun StatisticsNoneState(
    modifier: Modifier = Modifier,
    searchedTerm: String,
    statisticsClickEvents: (StatisticsEvents) -> Unit,
    listOfSearchedWorkouts: SnapshotStateList<WorkoutCategoryByJournal>
) {
    val searchText = rememberSaveable(searchedTerm) {
        mutableStateOf(searchedTerm)
    }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.spacing16)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                keyboardController?.hide()
                focusManager.clearFocus(true)
            }
    ) {
        SearchBar(
            searchedTerm = searchText.value,
            updateSearch = { searchedText ->
                statisticsClickEvents(
                    StatisticsEvents.FilterSearchByWorkout(
                        searchedText
                    )
                )
            },
            clearSearch = {
                statisticsClickEvents(StatisticsEvents.ClearSearchBarFilter)
            },
            keyboardController = keyboardController,
            focusManager = focusManager
        )
        UserWorkoutList(
            workoutList = listOfSearchedWorkouts,
            selectedWorkout = { name ->
                statisticsClickEvents(StatisticsEvents.GetWorkoutStats(name))
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun UserWorkoutList(
    workoutList: SnapshotStateList<WorkoutCategoryByJournal>,
    selectedWorkout: (WorkoutName) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Spacing.spacing8)
    ) {
        workoutList.forEach { childList ->
            val (workoutInitial, workouts) = childList
            if (workouts.isNotEmpty()) {
                stickyHeader {
                    CategoryHeader(text = workoutInitial)
                }

                itemsIndexed(workouts) { index, exercise ->
                    TextButton(
                        onClick = {
                            selectedWorkout(
                                exercise.workoutDetailsModel.name
                            )
                        }
                    ) {
                        ExerciseItem(exercise = exercise.workoutDetailsModel.name)
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
