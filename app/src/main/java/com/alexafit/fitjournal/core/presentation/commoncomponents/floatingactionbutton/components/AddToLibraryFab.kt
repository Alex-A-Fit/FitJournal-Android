package com.alexafit.fitjournal.core.presentation.commoncomponents.floatingactionbutton.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun AddToLibraryFab(
    navigateToAddWorkoutToLibraryScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.offset(y = -Spacing.spacing75),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextAndFab(
            onClickEvent = navigateToAddWorkoutToLibraryScreen,
            textId = R.string.text_add_to_library,
            iconId = R.drawable.icon_search,
            iconContentDescId = R.string.content_desc_library_search_fab
        )
    }
}
