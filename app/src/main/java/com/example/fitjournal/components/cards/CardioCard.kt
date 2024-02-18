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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.components.cards.subcomponents.CardSeeDetailsText
import com.example.fitjournal.components.cards.subcomponents.CardTitle
import com.example.fitjournal.components.cards.subcomponents.FitJournalCard
import com.example.fitjournal.components.cards.subcomponents.MostRecentWorkoutSession
import com.example.fitjournal.model.domain.CardioModel
import com.example.fitjournal.theme.Spacing

@Composable
fun CardioCard(
    cardioModel: List<CardioModel>,
    name: String,
    icon: Int,
) {
    val cardioSession by remember {
        mutableStateOf(
            cardioModel.last()
        )
    }
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
            MostRecentWorkoutSession()
            CardioSummary(
                cardioModel = cardioSession,
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
private fun CardioSummary(
    cardioModel: CardioModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(
                id = R.string.text_total_distance_traveled,
                cardioModel.distance,
                cardioModel.distanceType.stringValue
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = stringResource(
                id = R.string.text_total_time_elapsed,
                cardioModel.time
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}