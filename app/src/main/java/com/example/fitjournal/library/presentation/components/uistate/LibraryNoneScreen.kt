package com.example.fitjournal.library.presentation.components.uistate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.AddToJournalButton
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun LibraryNoneScreen(
    modifier: Modifier,
    openAddWorkoutToLibraryDialog: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_search),
            contentDescription = stringResource(id = R.string.content_desc_library_filled_icon),
            modifier = Modifier.size(Spacing.spacing128),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(Spacing.spacing16))
        Text(
            text = stringResource(id = R.string.title_no_workouts_in_library_for_add_workout),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(Spacing.spacing16))
        AddToJournalButton(
            textId = R.string.text_add_to_library,
            navigate = openAddWorkoutToLibraryDialog
        )
    }
}
