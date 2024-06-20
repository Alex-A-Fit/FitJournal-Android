package com.alexafit.fitjournal.home.presentation.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R

@Composable
fun NoWorkoutSetsErrorText(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.error_no_details_found),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
    )
}
