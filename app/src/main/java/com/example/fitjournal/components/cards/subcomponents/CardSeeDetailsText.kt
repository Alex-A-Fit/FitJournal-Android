package com.example.fitjournal.components.cards.subcomponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R

@Composable
fun CardSeeDetailsText(modifier: Modifier = Modifier){
    Text(
        text = stringResource(id = R.string.text_view_workout_details),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Light
    )
}