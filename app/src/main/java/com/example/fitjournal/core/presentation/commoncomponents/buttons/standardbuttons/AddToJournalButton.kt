package com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun AddToJournalButton(
    textId: Int = R.string.text_add_to_journal,
    navigate: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing16, horizontal = Spacing.spacing32)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.large
            ),
        onClick = { navigate() }
    ) {
        Text(
            text = stringResource(id = textId),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = Spacing.spacing8,
                    horizontal = Spacing.spacing16
                ),
            textAlign = TextAlign.Center
        )
    }
}
