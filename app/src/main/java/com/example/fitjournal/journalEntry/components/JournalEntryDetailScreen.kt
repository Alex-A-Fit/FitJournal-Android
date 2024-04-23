package com.example.fitjournal.journalEntry.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.journalEntry.domain.JournalEntryDetailsViewModel

@Composable
fun JournalEntryDetailsScreen(
    modifier: Modifier,
    viewModel: JournalEntryDetailsViewModel
) {
    Text(text = "Details entry Screen")
}
