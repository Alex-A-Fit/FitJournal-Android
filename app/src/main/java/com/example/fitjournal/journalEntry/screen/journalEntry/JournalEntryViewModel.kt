package com.example.fitjournal.journalEntry.screen.journalEntry

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary

class JournalEntryViewModel : ViewModel() {

    private val workoutList = MockData.mockLibraryList

    var selectedWorkoutDetail: MutableState<RealmWorkoutLibrary?> = mutableStateOf(null)

    fun searchWorkout(searchValue: String): List<RealmWorkoutLibrary> {
        return if (searchValue.isNotEmpty()) {
            workoutList.filter { it.name.contains(searchValue, ignoreCase = true) }
        } else {
            workoutList
        }
    }

    fun save() {
    }
}
