package com.example.fitjournal.commoncomponents.floatingactionbutton.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fitjournal.R
import com.example.fitjournal.theme.Spacing

@Composable
fun AddToJournalFab(
    navigateToAddWorkoutToJournalScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.offset(y = -Spacing.spacing128),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextAndFab(
            onClickEvent = navigateToAddWorkoutToJournalScreen,
            textId = R.string.text_add_to_journal,
            iconId = R.drawable.icon_journal,
            iconContentDescId = R.string.content_desc_journal_fab
        )
    }
}
