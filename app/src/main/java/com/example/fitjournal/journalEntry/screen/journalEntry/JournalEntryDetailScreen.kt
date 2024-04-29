package com.example.fitjournal.journalEntry.screen.journalEntry

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.components.datepicker.FitJournalDatePickerDialog
import com.example.fitjournal.journalEntry.components.CalisthenicsDataInput
import com.example.fitjournal.journalEntry.components.CardioDataInput
import com.example.fitjournal.journalEntry.components.WeighLiftDataInput
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun JournalEntryDetailsScreen(
    modifier: Modifier,
    viewModel: JournalEntryViewModel
) {
    var isDatePickerShowing = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isDatePickerShowing.value) {
            FitJournalDatePickerDialog(
                currentDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000,
                selectDate = { selectedDate ->
                    viewModel.pickerDate.value =
                        DateManager.getSelectedDate(selectedDate).localDateString
                    isDatePickerShowing.value = false
                },
                dismissDialog = {
                    isDatePickerShowing.value = false
                }
            )
        }

        viewModel.selectedWorkoutDetail?.let { selectedWorkoutDetail ->
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    isDatePickerShowing.value = true
                }
            ) {
                Text(
                    text = "Current Date Chosen: ${viewModel.pickerDate.value}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Left
                )
                Spacer(modifier = Modifier.width(Spacing.spacing4))
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "Icon to Edit Date",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(Spacing.spacing4))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedWorkoutDetail.value?.name ?: "Name not Available",
                    modifier = Modifier.padding(end = Spacing.spacing8),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = viewModel.getWorkoutTitle(),
                    modifier = Modifier,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Blue
                )
            }
            Spacer(modifier = Modifier.height(Spacing.spacing4))

            when (viewModel.mapToWorkoutEnum()) {
                WorkoutTypeEnum.WEIGHT_TRAINING -> {
                    WeighLiftDataInput(
                        reps = viewModel.reps,
                        weight = viewModel.weight,
                        sets = viewModel.sets
                    )
                }

                WorkoutTypeEnum.CALISTHENICS -> {
                    CalisthenicsDataInput(
                        reps = viewModel.reps,
                        weight = viewModel.weight,
                        distance = viewModel.distance,
                        duration = viewModel.duration
                    )
                }

                WorkoutTypeEnum.CARDIO -> {
                    CardioDataInput(
                        distance = viewModel.distance,
                        duration = viewModel.duration
                    )
                }

                null -> { }
            }
            Spacer(modifier = Modifier.height(Spacing.spacing4))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.spacing24),
                enabled = viewModel.isAddSetButtonEnabled.value,
                onClick = {
                    viewModel.addSet()
                }
            ) {
                Text(text = "+ Add Set")
            }

            LazyColumn(
                modifier = modifier.fillMaxWidth()
            ) {
                when (viewModel.mapToWorkoutEnum()) {
                    WorkoutTypeEnum.WEIGHT_TRAINING -> {
                        if (viewModel.weightLiftingSets.isNotEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.LightGray),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "# of Sets",
                                        modifier = Modifier
                                            .weight(2f, fill = false)
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Reps per Set",
                                        modifier = Modifier
                                            .weight(2f, fill = false)
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Weight",
                                        modifier = Modifier
                                            .weight(1f, fill = false)
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = " ",
                                        modifier = Modifier.weight(0.5f, fill = true),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            itemsIndexed(viewModel.weightLiftingSets) { index, item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            // click to edit workout row
                                        },
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.sets.toString(),
                                        modifier = Modifier
                                            .weight(2f, fill = false)
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = item.reps.toString(),
                                        modifier = Modifier
                                            .weight(2f, fill = false)
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = item.weight.toString(),
                                        modifier = Modifier
                                            .weight(1f, fill = false)
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    Icon(
                                        modifier = Modifier
                                            .clickable {
                                                viewModel.deleteWeightLiftSet(index)
                                            }
                                            .weight(0.5f, fill = true),
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Trash icon to delete the individual workout row",
                                        tint = Color.Red
                                    )
                                }
                                if (index != viewModel.weightLiftingSets.lastIndex) {
                                    Spacer(modifier = Modifier.height(Spacing.spacing8))
                                }
                            }
                        }
                    }

                    WorkoutTypeEnum.CALISTHENICS -> {
//                        items(viewModel.calisthenicsSets) {
//                        }
                    }

                    WorkoutTypeEnum.CARDIO -> {
//                        items(viewModel.cardioSets) {
//                        }
                    }

                    null -> { }
                }
            }
        }
    }
}
