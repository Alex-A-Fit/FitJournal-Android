package com.example.fitjournal.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.R
import com.example.fitjournal.model.enum.WorkoutTypeEnum
import com.example.fitjournal.model.events.HomeScreenEvents
import com.example.fitjournal.model.ui.FilterWorkoutModel
import com.example.fitjournal.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterJournalDialog(
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    workoutList: List<FilterWorkoutModel>,
    homeScreenEvents: (HomeScreenEvents) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = {
            homeScreenEvents(HomeScreenEvents.DismissFilterExercisesDialog)
        },
        modifier = modifier,
        properties = properties,
        content = {
            FilterJournalSection(
                modifier = modifier,
                workoutList = workoutList,
                onDismissDialog = {
                    homeScreenEvents(HomeScreenEvents.DismissFilterExercisesDialog)
                },
                onConfirmDialog = {
                    homeScreenEvents(HomeScreenEvents.OnConfirmFilterExercisesDialog(it))
                }
            )
        }
    )
}

@Composable
fun FilterJournalSection(
    modifier: Modifier = Modifier,
    workoutList: List<FilterWorkoutModel>,
    onDismissDialog: () -> Unit,
    onConfirmDialog: (List<WorkoutTypeEnum>) -> Unit
) {
    val mutableWorkoutList: MutableList<WorkoutTypeEnum> = workoutList.mapNotNull {
        if (it.isWorkoutSelected) {
            it.exerciseType
        } else {
            null
        }
    }.toMutableList()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FilterJournalDialogTitle()
        Spacer(modifier = Modifier.height(Spacing.spacing12))
        FilterJournalDialogSubtitle()
        Spacer(modifier = Modifier.height(Spacing.spacing12))
        FilterJournalDialogCheckboxes(
            workoutList = workoutList,
            onFilterCheckboxChanged = { workoutTypeEnum, checkboxValue ->
                if (checkboxValue) {
                    if (!mutableWorkoutList.contains(workoutTypeEnum)) {
                        mutableWorkoutList.add(workoutTypeEnum)
                    }
                } else {
                    if (mutableWorkoutList.contains(workoutTypeEnum)) {
                        mutableWorkoutList.remove(workoutTypeEnum)
                    }
                }
            }
        )
        FilterJournalDialogButtons(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing12),
            onDismissDialog = {
                mutableWorkoutList.clear()
                onDismissDialog()
            },
            onConfirmDialog = {
                onConfirmDialog(mutableWorkoutList.toList())
                onDismissDialog()
            }
        )
    }
}

@Composable
fun FilterJournalDialogTitle() {
    Text(
        text = stringResource(id = R.string.title_filter_workouts),
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun FilterJournalDialogSubtitle() {
    Text(
        text = stringResource(id = R.string.subtitle_filter_workouts_description),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun FilterJournalDialogCheckboxes(
    workoutList: List<FilterWorkoutModel>,
    onFilterCheckboxChanged: (WorkoutTypeEnum, Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        workoutList.forEach { workout ->
            FilterCheckboxItem(
                workout = workout,
                onFilterCheckboxChanged = { workoutTypeEnum, checkboxValue ->
                    onFilterCheckboxChanged(workoutTypeEnum, checkboxValue)
                }
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
        }
    }
}

@Composable
fun FilterCheckboxItem(
    workout: FilterWorkoutModel,
    onFilterCheckboxChanged: (WorkoutTypeEnum, Boolean) -> Unit
) {
    val workoutName = stringResource(id = workout.exerciseType.stringValue)
    var isFilterSelected by remember {
        mutableStateOf(workout.isWorkoutSelected)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isFilterSelected,
            onCheckedChange = {
                isFilterSelected = it
                // passing in the specific exercise toggled and the value of toggle
                onFilterCheckboxChanged(workout.exerciseType, it)
            }
        )
        Spacer(modifier = Modifier.height(Spacing.spacing8))
        WorkoutName(
            workoutName = workoutName,
            modifier = Modifier
                .weight(1F, fill = true)
        )
    }
}

@Composable
fun WorkoutName(
    workoutName: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = workoutName,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
        modifier = modifier
    )
}

@Composable
fun FilterJournalDialogButtons(
    modifier: Modifier,
    onDismissDialog: () -> Unit,
    onConfirmDialog: () -> Unit
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = { onDismissDialog() },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(
                        topStart = Spacing.spacing12,
                        bottomStart = Spacing.spacing12
                    )
                )
        ) {
            Text(
                text = stringResource(id = R.string.text_dismiss),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        VerticalDivider(
            thickness = Spacing.spacing2,
            color = MaterialTheme.colorScheme.onPrimary
        )
        TextButton(
            onClick = { onConfirmDialog() },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(
                        topEnd = Spacing.spacing12,
                        bottomEnd = Spacing.spacing12
                    )
                )
        ) {
            Text(
                text = stringResource(id = R.string.text_confirm),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
