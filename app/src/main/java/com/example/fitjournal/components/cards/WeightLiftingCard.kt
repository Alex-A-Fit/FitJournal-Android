package com.example.fitjournal.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.fitjournal.model.domain.WeightLiftingModel
import com.example.fitjournal.theme.Spacing

@Composable
fun WeightLiftingCard(
    weightLiftingModel: List<WeightLiftingModel>,
    name: String,
    icon: Int
) {
    val topSet by remember {
        mutableStateOf(
            weightLiftingModel.maxBy { set ->
                set.weight
            }
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
            TopSetTitle()
            TopSetSummary(
                reps = topSet.reps.toString(),
                weight = topSet.weight.toString(),
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
    reps: String,
    weight: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.text_total_reps, reps),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = stringResource(id = R.string.text_total_weight, weight),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
