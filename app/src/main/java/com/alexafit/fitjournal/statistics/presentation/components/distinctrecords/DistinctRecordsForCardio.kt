package com.alexafit.fitjournal.statistics.presentation.components.distinctrecords

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.DistinctPrBadgeColor
import com.alexafit.fitjournal.core.presentation.theme.DistinctPrBadgeColorVariant
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.statistics.domain.model.DistinctDistanceForTime

@Composable
fun DistinctRecordsForCardio(
    bestDistinctDistanceForTime: List<DistinctDistanceForTime>,
    modifier: Modifier = Modifier
) {
    val isDarkMode = isSystemInDarkTheme()

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.title_best_mile_times),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Spacing.spacing8))
        bestDistinctDistanceForTime.forEach {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = if (isDarkMode) DistinctPrBadgeColorVariant else DistinctPrBadgeColor,
                        shape = RoundedCornerShape(Spacing.spacing12)
                    )
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    ),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = it.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Spacing.spacing8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.text_best_mile_with_label,
                            it.distance
                        ),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = it.time,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.height(Spacing.spacing8))
        }
    }
}
