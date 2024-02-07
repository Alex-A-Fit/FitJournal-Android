package com.example.fitjournal.components.appbars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.R
import com.example.fitjournal.components.datepicker.FitJournalDatePicker
import com.example.fitjournal.components.dialogs.FilterJournalDialog
import com.example.fitjournal.components.icons.FilterIcon
import com.example.fitjournal.model.enum.WorkoutTypeEnum
import com.example.fitjournal.model.ui.FilterWorkoutModel
import com.example.fitjournal.theme.Spacing

@Composable
fun HomeTopAppBar(
    getPreviousDate: () -> Unit,
    getNextDate: () -> Unit,
    showDatePickerDialog: () -> Unit,
    currentDate: String,
) {
    var isFilterDialogShowing by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        appBarTitle = {
            FitJournalDatePicker(
                modifier = Modifier,
                getPreviousDate = { getPreviousDate() },
                getNextDate = { getNextDate() },
                currentDate = currentDate,
                showDatePickerDialog = {
                    showDatePickerDialog()
                }
            )
        },
        modifier = Modifier.fillMaxWidth(),
        endAlignedActionIcon = {
            FilterIcon(
                modifier = Modifier.size(Spacing.spacing32),
                contentDescription = stringResource(id = R.string.content_desc_home_screen_filter_icon),
                onClick = {
                    isFilterDialogShowing = true
                }
            )
        }
    )

    if (isFilterDialogShowing){
        FilterJournalDialog(
            modifier = Modifier,
            properties = DialogProperties(),
            workoutList = listOf(
                FilterWorkoutModel(
                    isWorkoutSelected = false,
                    exerciseType = WorkoutTypeEnum.CALISTHENICS
                ),
                FilterWorkoutModel(
                    isWorkoutSelected = false,
                    exerciseType = WorkoutTypeEnum.WEIGHT_TRAINING
                ),
                FilterWorkoutModel(
                    isWorkoutSelected = false,
                    exerciseType = WorkoutTypeEnum.CARDIO
                )
            ),
            onDismissDialog = {},
            onConfirmDialog = {},
            onFilterSelected = {})
    }
}