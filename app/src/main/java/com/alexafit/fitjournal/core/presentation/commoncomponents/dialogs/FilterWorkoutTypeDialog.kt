package com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.window.DialogProperties
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.BasicDialog
import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.home.presentation.model.ui.FilterWorkoutUiModel

@Composable
fun FilterWorkoutTypeDialog(
    properties: DialogProperties = DialogProperties(),
    filterList: SnapshotStateList<FilterWorkoutUiModel>,
    onDismissDialog: () -> Unit,
    clearFilterList: () -> Unit,
    onConfirmDialog: (List<WorkoutTypeEnum>) -> Unit
) {
    BasicDialog(
        dismissEvent = { onDismissDialog() },
        properties = properties
    ) {
        FilterJournalSection(
            filterList = filterList,
            onDismissDialog = { onDismissDialog() },
            onConfirmDialog = { listOfWorkoutTypes ->
                onConfirmDialog(listOfWorkoutTypes)
            },
            clearFilterList = clearFilterList
        )
    }
}

@Composable
private fun FilterJournalSection(
    filterList: SnapshotStateList<FilterWorkoutUiModel>,
    onDismissDialog: () -> Unit,
    clearFilterList: () -> Unit,
    onConfirmDialog: (List<WorkoutTypeEnum>) -> Unit
) {
    val mutableWorkoutList: MutableList<WorkoutTypeEnum> = filterList.mapNotNull {
        if (it.isWorkoutFilterSelected) {
            it.exerciseType
        } else {
            null
        }
    }.toMutableList()

    var wasClearFilterTextClicked by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = wasClearFilterTextClicked) {
        if (wasClearFilterTextClicked) {
            filterList.forEach {
                it.isWorkoutFilterSelected = true
            }
            wasClearFilterTextClicked = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Spacing.spacing12,
                vertical = Spacing.spacing24
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(Spacing.spacing16)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FilterJournalDialogTitle()
        FilterJournalDialogSubtitle()
        FilterJournalDialogCheckboxes(
            workoutList = filterList,
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
        FilterJournalDialogClearFilterText(
            clearFilterList = {
                clearFilterList()
                wasClearFilterTextClicked = true
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
private fun FilterJournalDialogTitle() {
    Text(
        text = stringResource(id = R.string.title_filter_workouts),
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(Spacing.spacing12))
}

@Composable
private fun FilterJournalDialogSubtitle() {
    Text(
        text = stringResource(id = R.string.subtitle_filter_workouts_description),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(Spacing.spacing12))
}

@Composable
private fun FilterJournalDialogCheckboxes(
    workoutList: SnapshotStateList<FilterWorkoutUiModel>,
    onFilterCheckboxChanged: (WorkoutTypeEnum, Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        workoutList.forEach { workout ->
            FilterCheckboxItem(
                onFilterCheckboxChanged = { workoutTypeEnum, checkboxValue ->
                    onFilterCheckboxChanged(workoutTypeEnum, checkboxValue)
                    workout.isWorkoutFilterSelected = checkboxValue
                },
                workoutType = workout.exerciseType,
                isCheckboxSelected = workout.isWorkoutFilterSelected
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
        }
    }
}

@Composable
private fun FilterJournalDialogClearFilterText(
    clearFilterList: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.text_clear_filter),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .clickable {
                    clearFilterList()
                },
            textDecoration = TextDecoration.Underline,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(Spacing.spacing12))
    }
}

@Composable
private fun FilterCheckboxItem(
    workoutType: WorkoutTypeEnum,
    isCheckboxSelected: Boolean,
    onFilterCheckboxChanged: (WorkoutTypeEnum, Boolean) -> Unit
) {
    val workoutName = stringResource(id = workoutType.stringId)
    val (isFilterSelected, setIsFilterSelected) = rememberSaveable(isCheckboxSelected) {
        mutableStateOf(isCheckboxSelected)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8)
            .clickable {
                setIsFilterSelected(!isFilterSelected)
                // passing in the specific exercise toggled and the value of toggle
                onFilterCheckboxChanged(workoutType, !isFilterSelected)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isFilterSelected,
            // moving the check change to Parent Row
            onCheckedChange = null
        )
        Spacer(modifier = Modifier.width(Spacing.spacing12))
        WorkoutName(
            workoutName = workoutName,
            modifier = Modifier
                .weight(1F, fill = true)
        )
    }
}

@Composable
private fun WorkoutName(
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
private fun FilterJournalDialogButtons(
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
                text = stringResource(id = R.string.button_cancel),
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
                text = stringResource(id = R.string.button_confirm),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
