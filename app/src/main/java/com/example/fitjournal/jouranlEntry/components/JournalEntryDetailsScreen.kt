package com.example.fitjournal.jouranlEntry.components

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.components.datepicker.FitJournalDatePickerDialog
import com.example.fitjournal.jouranlEntry.domain.JournalEntryDetailsViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun JournalEntryDetailsScreen(
    modifier: Modifier,
    viewModel: JournalEntryDetailsViewModel
) {

    var isDatePickerShowing = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(isDatePickerShowing.value) {
            FitJournalDatePickerDialog(
                currentDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000,
                selectDate = { selectedDate ->
                    viewModel.pickerDate.value = DateManager.getSelectedDate(selectedDate).localDateString
                    isDatePickerShowing.value = false
                },
                dismissDialog = {
                    isDatePickerShowing.value = false
                }
            )
        }

        viewModel.selectedWorkoutDetail?.let {  selectedWorkoutDetail ->
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    isDatePickerShowing.value = true
                }) {
                Text(
                    text = "Date: ${viewModel.pickerDate.value}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Left
                )
            }
            Spacer(modifier = Modifier.height(Spacing.spacing4))

            Text(
                text = stringResource(id = selectedWorkoutDetail.workoutType.workoutTitle()),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Spacing.spacing16),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Blue,
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(Spacing.spacing4))

            when(selectedWorkoutDetail.workoutType) {
                WorkoutTypeEnum.WEIGHT_TRAINING -> {
                    WeighLiftDataInput(
                        reps = viewModel.reps,
                        weight = viewModel.weight
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

                when(selectedWorkoutDetail.workoutType) {
                    WorkoutTypeEnum.WEIGHT_TRAINING -> {
                        if(viewModel.weightLiftingSets.value.isNotEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.LightGray),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "Set", modifier = Modifier.fillMaxWidth(0.25f), textAlign = TextAlign.Center)
                                    Text(text = "Reps", modifier = Modifier.fillMaxWidth(0.25f), textAlign = TextAlign.Center)
                                    Text(text = "Weight", modifier = Modifier.fillMaxWidth(0.25f), textAlign = TextAlign.Center)
                                    Text(text = " ", modifier = Modifier.fillMaxWidth(0.25f), textAlign = TextAlign.Center)
                                }
                            }
                            itemsIndexed(viewModel.weightLiftingSets.value) {index, item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    //ALEX: WHAT IS THE BEST WAY TO MAKE THIS THREE OBJECTS SELECTABLE AT THE SAME TIME, WHILE KEEPING THE 0.25F PROPORSION IN THE SCREEN
                                    Text(text = "${index+1}", modifier = Modifier.fillMaxWidth(0.25f), textAlign = TextAlign.Center)
                                    Text(text = "${item.reps.toString()}", modifier = Modifier.fillMaxWidth(0.25f), textAlign = TextAlign.Center)
                                    Text(text = "${item.weight.toString()}", modifier = Modifier.fillMaxWidth(0.25f), textAlign = TextAlign.Center)
                                    Icon(
                                        modifier = Modifier.fillMaxWidth(0.25f)
                                            .clickable {
                                                //ALEX: WHY IS THIS NOT UPDATING THE VIEW, IT DOES REMOVE THE OBJECT FROM THE ARRAY CORRECTLY
                                                viewModel.deleteWeightLiftSet(index)
                                            },
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                    WorkoutTypeEnum.CALISTHENICS -> {
                        items(viewModel.calisthenicsSets.value) {

                        }
                    }
                    WorkoutTypeEnum.CARDIO -> {
                        items(viewModel.cardioSets.value) {

                        }
                    }
                }
            }

        }
    }
}

@Composable
fun WeighLiftDataInput(
    reps: MutableState<String>,
    weight: MutableState<String>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing16)
    ) {
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            FitJournalTextField(header = "Reps", textValue = reps.value, placeholder = "0") {
                reps.value = it
            }
        }
        Spacer(modifier = Modifier.width(Spacing.spacing32))
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            FitJournalTextField(header = "Weight", textValue = weight.value, placeholder = "0") {
                weight.value = it
            }
        }
    }
}

@Composable
fun CardioDataInput(
    distance: MutableState<String>,
    duration: MutableState<String>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing16)
    ) {
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            FitJournalTextField(header = "Reps", textValue = distance.value, placeholder = "0") {
                distance.value = it
            }
        }
        Spacer(modifier = Modifier.width(Spacing.spacing32))
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            FitJournalTextField(header = "Weight", textValue = duration.value, placeholder = "0") {
                duration.value = it
            }
        }
    }
}

@Composable
fun CalisthenicsDataInput(
    reps: MutableState<String>,
    weight: MutableState<String>,
    distance: MutableState<String>,
    duration: MutableState<String>
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing16)
        ) {
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                FitJournalTextField(header = "Reps", textValue = reps.value, placeholder = "0") {
                    reps.value = it
                }
            }
            Spacer(modifier = Modifier.width(Spacing.spacing32))
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                FitJournalTextField(header = "Weight", textValue = weight.value, placeholder = "0") {
                    weight.value = it
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.spacing16)
        ) {
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                FitJournalTextField(header = "Reps", textValue = distance.value, placeholder = "0") {
                    distance.value = it
                }
            }
            Spacer(modifier = Modifier.width(Spacing.spacing32))
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                FitJournalTextField(header = "Weight", textValue = duration.value, placeholder = "0") {
                    duration.value = it
                }
            }
        }
    }
}

@Composable
fun FitJournalTextField(
    textValue: String,
    header: String,
    placeholder: String,
    updatedValue: (String) -> Unit
) {
    val localKeyboard = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = textValue,
        onValueChange = { value ->
            updatedValue(value)
        },
        label = {
            Text(
                text = header,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary
            )
        },
        trailingIcon = { },
        textStyle = MaterialTheme.typography.bodyMedium,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = androidx.compose.ui.text.input.ImeAction.Done,
            capitalization = KeyboardCapitalization.Words
        ),
        keyboardActions = KeyboardActions(
            onDone = { localKeyboard?.hide() }
        ),
        singleLine = true
    )
}
