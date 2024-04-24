package com.example.fitjournal.journalEntry.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.journalEntry.domain.JournalEntryViewModel

@Composable
fun JournalEntryDetailsScreen(
    modifier: Modifier,
    viewModel: JournalEntryViewModel
) {
    Text(text = "Details entry Screen")
}
