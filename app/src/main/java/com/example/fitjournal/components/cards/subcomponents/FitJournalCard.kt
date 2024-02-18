package com.example.fitjournal.components.cards.subcomponents

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.theme.Spacing

@Composable
fun FitJournalCard(
    modifier: Modifier = Modifier,
    workoutContent: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = Spacing.spacing16
        )
    ) {
        workoutContent()
    }
}
