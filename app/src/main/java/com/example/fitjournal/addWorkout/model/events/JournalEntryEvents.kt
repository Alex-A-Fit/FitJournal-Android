package com.example.fitjournal.addWorkout.model.events

sealed class JournalEntryEvents {
    data object ClearSearchBarFilter : JournalEntryEvents()
    data class FilterSearchByWorkout(val workout: String) : JournalEntryEvents()
}
