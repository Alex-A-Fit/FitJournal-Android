package com.example.fitjournal.components.cards

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
import com.example.fitjournal.components.cards.subcomponents.CardSeeDetailsText
import com.example.fitjournal.components.cards.subcomponents.CardTitle
import com.example.fitjournal.components.cards.subcomponents.FitJournalCard
import com.example.fitjournal.theme.Spacing

@Composable
fun WeightLiftingCard(
    reps: Int?,
    weight: Double?,
    name: String,
    icon: Int
) {
    FitJournalCard(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            CardTitle(
                title = name,
                workoutIcon = icon,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    )
            )
            Spacer(modifier = Modifier.height(Spacing.spacing8))
            TopSetTitle()
            TopSetSummary(
                reps = reps,
                weight = weight,
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing16)
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
private fun TopSetTitle() {
    Text(
        text = stringResource(id = R.string.title_top_set),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = Spacing.spacing16)
    )
}

@Composable
private fun TopSetSummary(
    reps: Int?,
    weight: Double?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        reps?.let {
            Text(
                text = stringResource(id = R.string.text_total_reps, reps.toString()),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        weight?.let {
            Text(
                text = stringResource(id = R.string.text_total_weight, weight.toString()),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
