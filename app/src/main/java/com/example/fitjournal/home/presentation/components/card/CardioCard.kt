package com.example.fitjournal.home.presentation.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.cards.FitJournalCard
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.components.card.subcomponents.CardSeeDetailsText
import com.example.fitjournal.home.presentation.components.card.subcomponents.CardTitle
import com.example.fitjournal.home.presentation.components.card.subcomponents.MostRecentWorkoutSessionTitle
import com.example.fitjournal.home.presentation.components.text.NoWorkoutSetsErrorText
import com.example.fitjournal.home.presentation.model.ui.CardioUi

@Composable
fun CardioCard(
    cardioUi: CardioUi,
    modifier: Modifier = Modifier
) {
    FitJournalCard(modifier = modifier) {
        Column {
            CardTitle(
                title = cardioUi.name,
                workoutIcon = cardioUi.icon,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    )
            )
            Spacer(modifier = Modifier.height(Spacing.spacing8))
            if (
                cardioUi.distance != null &&
                cardioUi.time != null
            ) {
                MostRecentWorkoutSessionTitle()
            } else {
                NoWorkoutSetsErrorText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.spacing16)
                )
            }
            CardioSummary(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing16),
                cardioUi = cardioUi
            )
            Spacer(modifier = Modifier.height(Spacing.spacing8))
            CardSeeDetailsText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing16)
            )
            Spacer(modifier = Modifier.height(Spacing.spacing8))
        }
    }
}

@Composable
private fun CardioSummary(
    cardioUi: CardioUi,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        if (
            cardioUi.distance != null &&
            cardioUi.time != null
        ) {
            Text(
                text = stringResource(
                    id = R.string.text_total_distance_traveled,
                    cardioUi.distance.toString(),
                    cardioUi.distanceType.stringValue
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            cardioUi.laps?.let {
                Text(
                    text = stringResource(
                        id = R.string.text_total_laps,
                        it.toString()
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = stringResource(
                    id = R.string.text_total_time_elapsed,
                    cardioUi.time
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
