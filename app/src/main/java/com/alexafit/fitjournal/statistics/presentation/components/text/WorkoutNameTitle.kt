package com.alexafit.fitjournal.statistics.presentation.components.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun WorkoutNameTitle(workoutName: String) {
    Text(
        text = workoutName,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Spacing.spacing16,
                start = Spacing.spacing16,
                end = Spacing.spacing16,
                bottom = Spacing.spacing8
            ),
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center
    )
}
