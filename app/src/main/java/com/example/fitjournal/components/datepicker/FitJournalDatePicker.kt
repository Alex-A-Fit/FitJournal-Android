package com.example.fitjournal.components.datepicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R
import com.example.fitjournal.theme.Spacing

@Composable
fun FitJournalDatePicker(
    modifier: Modifier,
    getPreviousDate: () -> Unit,
    getNextDate: () -> Unit,
    showDatePickerDialog: () -> Unit,
    currentDate: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { getPreviousDate() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_back),
                contentDescription = "Arrow to navigate backwards through the calendar",
                modifier = Modifier.size(Spacing.spacing32)
            )
        }

        Text(
            text = currentDate,
            modifier = Modifier
                .padding(horizontal = Spacing.spacing16)
                .weight(1F, fill = false)
                .clickable {
                    showDatePickerDialog()
                },
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = { getNextDate() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_forward),
                contentDescription = "Arrow to navigate forward through the calendar",
                modifier = Modifier.size(Spacing.spacing32)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitJournalDatePickerDialog(
    currentDate: Long,
    selectDate: (Long) -> Unit,
    dismissDialog: () -> Unit
) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = currentDate,
            initialDisplayedMonthMillis = currentDate
        )
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                dismissDialog()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val dateSelected = datePickerState.selectedDateMillis
                        if (dateSelected != null) {
                            selectDate(dateSelected)
                        }
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dismissDialog()
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
}