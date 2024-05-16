package com.example.fitjournal.journalEntry.screen.journalEntry

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
import com.example.fitjournal.core.util.filter.searchForText
import com.example.fitjournal.journalEntry.model.JournalEntryUiModel
import com.example.fitjournal.journalEntry.model.events.JournalEntryEvents
import com.example.fitjournal.library.presentation.screen.library.utils.mapToLibraryUiList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWorkoutViewModel @Inject constructor(
    private val realmWorkoutLibraryUseCase: RealmWorkoutLibraryUseCase
) : ViewModel() {

    private val workoutList = MockData.mockLibraryList

    var selectedWorkoutDetail: MutableState<RealmWorkoutLibrary?> = mutableStateOf(null)
    var journalEntryState by mutableStateOf(
        JournalEntryUiModel(
            handleJournalEntryClickEvents = ::journalClickEvents
        )
    )
        private set

    init {
        getDataFromRealmDb()
    }

    private fun journalClickEvents(event: JournalEntryEvents) {
        when (event) {
            is JournalEntryEvents.FilterSearchByWorkout -> {
                val filteredList = searchForText(
                    event.workout,
                    journalEntryState.masterWorkoutList
                )
                updateJournalEntryState(
                    newJournalEntryState = journalEntryState.copy(
                        listOfSearchedWorkouts = filteredList.toMutableStateList(),
                        searchedTerm = event.workout
                    )
                )
            }

            JournalEntryEvents.ClearSearchBarFilter -> {
                updateJournalEntryState(
                    newJournalEntryState = journalEntryState.copy(
                        listOfSearchedWorkouts = journalEntryState.masterWorkoutList.toMutableStateList(),
                        searchedTerm = ""
                    )
                )
            }
        }
    }

    private fun getDataFromRealmDb() {
        viewModelScope.launch {
            val workoutList = realmWorkoutLibraryUseCase.getRealmWorkoutLibraryList()
            if (workoutList.isNotEmpty()) {
                val libraryList = workoutList.groupBy { it.workoutType }.toSortedMap()
                val masterWorkoutList = mapToLibraryUiList(libraryList)
                updateJournalEntryState(
                    newJournalEntryState = journalEntryState.copy(
                        masterWorkoutList = masterWorkoutList,
                        listOfSearchedWorkouts = if (journalEntryState.searchedTerm.isEmpty()) {
                            masterWorkoutList.toMutableStateList()
                        } else {
                            searchForText(
                                journalEntryState.searchedTerm,
                                journalEntryState.masterWorkoutList
                            ).toMutableStateList()
                        }
                    )
                )
            }
        }
    }

    private fun updateJournalEntryState(newJournalEntryState: JournalEntryUiModel) {
        journalEntryState = newJournalEntryState
    }
}
