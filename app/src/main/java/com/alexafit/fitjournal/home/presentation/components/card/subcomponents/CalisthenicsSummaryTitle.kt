package com.alexafit.fitjournal.home.presentation.components.card.subcomponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun CalisthenicsSummaryTitle() {
    Text(
        text = stringResource(id = R.string.title_calisthenics_session_summary),
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = Spacing.spacing16)
    )
}
