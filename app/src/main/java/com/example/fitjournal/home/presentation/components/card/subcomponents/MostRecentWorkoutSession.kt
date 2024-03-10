package com.example.fitjournal.home.presentation.components.card.subcomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.theme.Spacing

@Composable
fun MostRecentWorkoutSession() {
    Text(
        text = stringResource(id = R.string.title_most_recent_workout_session),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = Spacing.spacing16)
    )
}
