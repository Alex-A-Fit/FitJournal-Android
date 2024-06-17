package com.example.fitjournal.onboarding.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

@Composable
fun TypewriterText(
    text: String,
    onTextEffectComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var displayText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit) {
        for (i in 1..text.length) {
            displayText = text.substring(0, i)
            delay(20)
        }
        delay(2000)
        onTextEffectComplete()
    }
    Text(
        text = displayText,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge
    )
}
