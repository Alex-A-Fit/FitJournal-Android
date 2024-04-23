package com.example.fitjournal.journalEntry.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class JournalEntryDetailsViewModelFactory(private val selectedWorkoutDetail: WorkoutDetail?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        JournalEntryDetailsViewModel(selectedWorkoutDetail) as T
}
class JournalEntryDetailsViewModel(val selectedWorkoutDetail: WorkoutDetail?) : ViewModel() {

    fun save() {
    }
}
