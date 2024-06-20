package com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.core.util.constants.Constants.EMPTY_SPACE

@Composable
fun CalisthenicsListHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = Spacing.spacing24,
                    end = Spacing.spacing16
                )
        ) {
            Text(
                text = EMPTY_SPACE,
                modifier = Modifier.weight(0.5f, fill = true),
                textAlign = TextAlign.Start
            )
            Text(
                text = stringResource(id = R.string.label_sets),
                modifier = Modifier
                    .weight(2f, fill = false)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.label_reps),
                modifier = Modifier
                    .weight(2f, fill = false)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.label_weight),
                modifier = Modifier
                    .weight(2f, fill = false)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(id = R.string.label_time),
                modifier = Modifier
                    .weight(2f, fill = false)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = EMPTY_SPACE,
                modifier = Modifier.weight(0.5f, fill = true),
                textAlign = TextAlign.Center
            )
        }
    }
}
