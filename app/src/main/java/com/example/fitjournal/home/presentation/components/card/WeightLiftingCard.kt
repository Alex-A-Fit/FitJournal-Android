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
import com.example.fitjournal.core.util.extensions.TwoDecimalOrNoDecimal
import com.example.fitjournal.home.presentation.components.card.subcomponents.CardSeeDetailsText
import com.example.fitjournal.home.presentation.components.card.subcomponents.CardTitle
import com.example.fitjournal.home.presentation.components.text.NoWorkoutSetsErrorText
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType
import com.example.fitjournal.home.presentation.model.ui.WeightLiftingUi

@Composable
fun WeightLiftingCard(
    weightLiftingUi: WeightLiftingUi,
    modifier: Modifier = Modifier
) {
    FitJournalCard(modifier = modifier) {
        CardTitle(
            title = weightLiftingUi.name,
            workoutIcon = weightLiftingUi.icon,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Spacing.spacing16,
                    vertical = Spacing.spacing8
                ),
            isWeightTrainingIcon = true
        )
        Spacer(modifier = Modifier.height(Spacing.spacing8))
        if (weightLiftingUi.doesWeightLiftingPropertyExist()) {
            TopSetTitle()
            TopSetSummary(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing16),
                reps = weightLiftingUi.reps?.toString() ?: "",
                sets = weightLiftingUi.sets?.toString() ?: "",
                weight = weightLiftingUi.weight?.toString()?.TwoDecimalOrNoDecimal() ?: "",
                weightInKgs = weightLiftingUi.weightInKgs.toString().TwoDecimalOrNoDecimal()
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

@Composable
private fun TopSetTitle() {
    Text(
        text = stringResource(id = R.string.title_top_set),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(horizontal = Spacing.spacing16)
    )
}

@Composable
private fun TopSetSummary(
    reps: String,
    sets: String,
    weight: String,
    weightInKgs: String,
    modifier: Modifier = Modifier
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

        Text(
            text = stringResource(
                id = R.string.text_total_weight,
                weight,
                WeightLiftingWeightType.POUNDS.stringValue,
                weightInKgs,
                WeightLiftingWeightType.KILOGRAMS.stringValue
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
