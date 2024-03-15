package com.example.fitjournal.commoncomponents.cards.subcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.example.fitjournal.commoncomponents.icons.ExerciseTypeIcon
import com.example.fitjournal.theme.Spacing

@Composable
fun CardTitle(
    modifier: Modifier = Modifier,
    title: String,
    workoutIcon: Int,
    isWeightTrainingIcon: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        ExerciseTypeIcon(
            isWeightTrainingIcon = isWeightTrainingIcon,
            workoutIcon = workoutIcon
        )
        Spacer(modifier = Modifier.width(Spacing.spacing12))
        Text(
            text = title,
            modifier = Modifier
                .weight(1f, fill = true),
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
