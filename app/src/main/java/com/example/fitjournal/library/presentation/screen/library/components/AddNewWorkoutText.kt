package com.example.fitjournal.library.presentation.screen.library.components

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.utils.CreateAnnotatedString

@Composable
fun AddNewWorkoutText() {
    /**
     * if customHighlightedText parameter is not null then this is how you access the position of the highlighted substring
     * inside the ClickableText Composable
     * onClick = {offset ->
     *                val item = annotatedString.getStringAnnotations("customHighlight", offset, offset).firstOrNull()?.item
     *                if (item != null){
     *                    Log.d("TAG: ", "THE CLICKABLE TEXT WAS CLICKED")
     *                }
     *            }
     */
    val annotatedString = CreateAnnotatedString(text = stringResource(id = R.string.text_add_to_workout))

    ClickableText(
        modifier = Modifier.padding(bottom = Spacing.spacing8),
        text = annotatedString,
        onClick = {
            Log.d("LibraryScreen: ", "THE CLICKABLE TEXT WAS CLICKED")
        }
    )
}
