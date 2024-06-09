package com.example.fitjournal.library.presentation.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun createAnnotatedString(
    text: String,
    customHighlightedText: String? = null
): AnnotatedString {
    val startIndex = if (customHighlightedText == null) 0 else text.indexOf(customHighlightedText)
    val endIndex =
        if (customHighlightedText == null) text.length else startIndex + customHighlightedText.length

    return buildAnnotatedString {
        append(text)

        // general stayling of text
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            ),
            start = 0,
            end = text.length
        )

        // styling for custom highlighted or for text if custom highlighted text is null
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            ),
            start = startIndex,
            end = endIndex
        )

        /**
         * Leaving annotation parameter empty as it is not used for now
         */
        if (customHighlightedText != null) {
            addStringAnnotation(
                tag = "customHighlight",
                annotation = "",
                start = startIndex,
                end = endIndex
            )
        }
    }
}
