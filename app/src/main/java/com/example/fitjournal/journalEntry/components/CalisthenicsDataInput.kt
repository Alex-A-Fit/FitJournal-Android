package com.example.fitjournal.journalEntry.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.theme.Spacing

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
                DataEntryTextField(header = "Reps", textValue = reps.value, placeholder = "0") {
                    reps.value = it
                }
            }
            Spacer(modifier = Modifier.width(Spacing.spacing32))
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                DataEntryTextField(
                    header = "Weight",
                    textValue = weight.value,
                    placeholder = "0"
                ) {
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
                DataEntryTextField(
                    header = "Reps",
                    textValue = distance.value,
                    placeholder = "0"
                ) {
                    distance.value = it
                }
            }
            Spacer(modifier = Modifier.width(Spacing.spacing32))
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                DataEntryTextField(
                    header = "Weight",
                    textValue = duration.value,
                    placeholder = "0"
                ) {
                    duration.value = it
                }
            }
        }
    }
}