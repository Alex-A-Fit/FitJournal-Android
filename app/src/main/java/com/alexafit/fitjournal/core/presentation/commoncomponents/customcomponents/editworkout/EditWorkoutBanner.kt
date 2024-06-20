package com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.text.CommonSubtitleText
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun EditWorkoutBanner(
    workoutDate: String,
    openTimePicker: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Spacing.spacing12,
                    horizontal = Spacing.spacing16
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CommonSubtitleText(
                subtitleText = stringResource(id = R.string.text_chosen_date)
            )
            Spacer(modifier = Modifier.width(Spacing.spacing8))
            WorkoutDateText(
                workoutDate = workoutDate,
                openTimePicker
            )
        }
    }
}

@Composable
private fun WorkoutDateText(
    workoutDate: String,
    openTimePicker: () -> Unit
) {
    Text(
        text = workoutDate,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.clickable { openTimePicker() },
        color = MaterialTheme.colorScheme.inversePrimary,
        textDecoration = TextDecoration.Underline
    )
}
