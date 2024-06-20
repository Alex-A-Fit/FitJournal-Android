package com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.domain.model.CardioModel
import com.alexafit.fitjournal.core.presentation.theme.Red
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun CardioWorkoutSets(
    workout: CardioModel,
    deleteSet: () -> Unit,
    editSet: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Spacing.spacing24,
                end = Spacing.spacing16,
                top = Spacing.spacing8
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    editSet()
                }
                .weight(0.5f, fill = true),
            imageVector = Icons.Filled.Create,
            contentDescription = stringResource(id = R.string.content_desc_edit_workout_pencil_icon),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = workout.laps?.toString() ?: stringResource(id = R.string.text_not_applicable),
            modifier = Modifier
                .weight(2f, fill = false)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "${workout.distance} ${workout.distanceType.stringConcatenatedValue}",
            modifier = Modifier
                .weight(2f, fill = false)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text =
            "${workout.time.hours}:${workout.time.minutes}:${workout.time.seconds}",
            modifier = Modifier
                .weight(2f, fill = false)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            modifier = Modifier
                .clickable {
                    deleteSet()
                }
                .weight(0.5f, fill = true),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.content_desc_edit_workout_trash_icon),
            tint = Red
        )
    }
}
