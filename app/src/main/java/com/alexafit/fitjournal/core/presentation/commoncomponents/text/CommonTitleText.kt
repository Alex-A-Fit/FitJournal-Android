package com.alexafit.fitjournal.core.presentation.commoncomponents.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CommonTitleText(
    titleText: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = titleText,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}
