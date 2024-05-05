package com.example.fitjournal.core.presentation.commoncomponents.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CommonSubtitleText(
    subtitleText: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = subtitleText,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier,
        textAlign = TextAlign.Start
    )
}
