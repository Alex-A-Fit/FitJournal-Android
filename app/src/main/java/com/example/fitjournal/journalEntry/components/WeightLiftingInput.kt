package com.example.fitjournal.journalEntry.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun WeighLiftDataInput(
    sets: MutableState<String>,
    reps: MutableState<String>,
    weight: MutableState<String>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        DataEntryTextField(
            header = "Sets",
            modifier = Modifier
                .weight(1f)
                .padding(end = Spacing.spacing12),
            textValue = sets.value,
            placeholder = "0",
            updatedValue = {
                sets.value = it
            }
        )
        DataEntryTextField(
            header = "Reps",
            modifier = Modifier
                .weight(1f)
                .padding(end = Spacing.spacing12),
            textValue = reps.value,
            placeholder = "0",
            updatedValue = {
                reps.value = it
            }
        )
        DataEntryTextField(
            header = "Weight",
            textValue = weight.value,
            modifier = Modifier.weight(1f),
            placeholder = "0",
            updatedValue = {
                weight.value = it
            }
        )
    }
}