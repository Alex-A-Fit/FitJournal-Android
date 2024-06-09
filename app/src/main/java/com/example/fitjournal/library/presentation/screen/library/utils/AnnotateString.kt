package com.example.fitjournal.library.presentation.screen.library.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun createAnnotatedString(
    text: String,
    startIndex: Int = 0,
    endIndex: Int = text.length,
    customHighlightedText: String? = null
): AnnotatedString {
    return buildAnnotatedString {
        append(text)
        val start = if (customHighlightedText == null) startIndex else text.indexOf(customHighlightedText)
        val end = if (customHighlightedText == null) start + endIndex else start + customHighlightedText.length

        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            ),
            start = start,
            end = end
        )

        /**
         * if customHighlighted word is not null then this is how you access the position of the highlighted substring
         * inside the ClickableText Composable
         * onClick = {offset ->
         *                val item = annotatedString.getStringAnnotations("customHighlight", offset, offset).firstOrNull()?.item
         *                if (item != null){
         *                    Log.d("TAG: ", "THE CLICKABLE TEXT WAS CLICKED")
         *                }
         *            }
         */
        if (customHighlightedText != null) {
            addStringAnnotation(
                tag = "customHighlight",
                annotation = "",
                start = start,
                end = end
            )
        }
    }
}
