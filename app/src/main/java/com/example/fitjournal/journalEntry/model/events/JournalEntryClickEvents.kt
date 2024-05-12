package com.example.fitjournal.journalEntry.model.events

sealed class JournalEntryClickEvents {
    data object GetLibraryWorkouts: JournalEntryClickEvents()
}