package com.alexafit.fitjournal.library.presentation.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.library.presentation.utils.createAnnotatedString

@Composable
fun AddNewWorkoutText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
//
//      if customHighlightedText parameter is not null then this is how you access the position of the highlighted substring
//      inside the ClickableText Composable
//      onClick = {offset ->
//                     val item = annotatedString.getStringAnnotations("customHighlight", offset, offset).firstOrNull()?.item
//                     if (item != null){
//                         Log.d("TAG: ", "THE CLICKABLE TEXT WAS CLICKED")
//                    }
//                }
//
    val annotatedString = createAnnotatedString(
        text = stringResource(id = R.string.text_add_to_workout),
        customHighlightedText = stringResource(id = R.string.text_add_to_workout_custom_highlight)
    )

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            val item = annotatedString.getStringAnnotations("customHighlight", offset, offset)
                .firstOrNull()?.item
            if (item != null) {
                onClick()
            }
        }
    )
}
