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
import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.core.presentation.commoncomponents.cards.FitJournalCard
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.components.card.subcomponents.CardSeeDetailsText
import com.example.fitjournal.home.presentation.components.card.subcomponents.CardTitle
import com.example.fitjournal.home.presentation.components.card.subcomponents.MostRecentWorkoutTitle
import com.example.fitjournal.home.presentation.components.text.NoWorkoutSetsErrorText
import com.example.fitjournal.home.presentation.model.ui.CalisthenicsUi

@Composable
fun CalisthenicsCard(
    calisthenicsUi: CalisthenicsUi,
    modifier: Modifier = Modifier
) {
    FitJournalCard(modifier = modifier) {
        Column {
            CardTitle(
                title = calisthenicsUi.name,
                workoutIcon = calisthenicsUi.icon,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing16,
                        vertical = Spacing.spacing8
                    )
            )
            Spacer(modifier = Modifier.height(Spacing.spacing8))
            if (calisthenicsUi.doesRepsAndSetsExist()) {
                MostRecentWorkoutTitle()
                MostRecentCalisthenicsSet(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.spacing16),
                    sets = calisthenicsUi.sets?.toString() ?: "",
                    reps = calisthenicsUi.reps?.toString() ?: "",
                    weight = calisthenicsUi.weight?.toString(),
                    time = calisthenicsUi.time
                )
            } else {
                NoWorkoutSetsErrorText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.spacing16)
                )
            }
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
private fun MostRecentCalisthenicsSet(
    sets: String,
    reps: String,
    modifier: Modifier = Modifier,
    weight: String? = null,
    time: TimeModel? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(
                id = R.string.text_total_sets,
                sets
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = stringResource(
                id = R.string.text_total_reps,
                reps
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        weight?.let {
            Text(
                text = stringResource(
                    id = R.string.text_total_weight,
                    it.toString()
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        time?.let {
            Text(
                text = stringResource(
                    id = R.string.text_total_time_elapsed,
                    "${it.hours}:${it.minutes}:${it.seconds}"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
