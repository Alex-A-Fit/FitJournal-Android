package com.example.fitjournal.components.cards.subcomponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CardSeeDetailsText(modifier: Modifier = Modifier){
    Text(
        text = "Click to view/edit workout",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Light
    )
}