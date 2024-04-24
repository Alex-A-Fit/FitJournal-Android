package com.example.fitjournal.library.presentation.screen.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fitjournal.core.domain.util.filtering.searchForText
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory
import com.example.fitjournal.library.presentation.screen.library.utils.mapToLibraryUiList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryScreenViewModel @Inject constructor() : ViewModel() {
    var libraryWorkoutState by mutableStateOf(
        LibraryWorkoutUiModel(
            handleLibraryWorkoutClickEvents = ::handleLibraryWorkoutEvents
        )
    )
        private set

    // will fix commented code in next PR  just creating Data and domain layer first

//    init {
//        // Dummy Data for now
//        val workoutMap = libraryWorkoutList.groupBy { it.first() }.toSortedMap()
//        val masterWorkoutList = mapToLibraryUiList(workoutMap)
//        setMasterListOfWorkouts(masterWorkoutList)
//    }

    private fun handleLibraryWorkoutEvents(event: LibraryWorkoutClickEvents) {
        when (event) {
            LibraryWorkoutClickEvents.ClearSearch -> clearSearch()
            is LibraryWorkoutClickEvents.UpdateSearch -> {
                updateSearchBarText(event.text)
                updateSearchedWorkouts(event.text)
            }
        }
    }

    private fun updateSearchedWorkouts(text: String) {
        // empty list for now until next PR that hooks up mock data
        val filteredList = searchForText(text, emptyList())
        val uiList = mapToLibraryUiList(filteredList)
        setListOfSearchedWorkouts(uiList)
    }

    private fun setMasterListOfWorkouts(workoutList: List<WorkoutCategory>) {
        libraryWorkoutState = libraryWorkoutState.copy(
            masterWorkoutList = workoutList,
            listOfSearchedWorkouts = workoutList
        )
    }

    private fun setListOfSearchedWorkouts(workoutList: List<WorkoutCategory>) {
        libraryWorkoutState = libraryWorkoutState.copy(
            listOfSearchedWorkouts = workoutList
        )
    }

    private fun updateSearchBarText(searchedText: String) {
        libraryWorkoutState = libraryWorkoutState.copy(
            searchedTerm = searchedText
        )
    }

    private fun clearSearch() {
        libraryWorkoutState = libraryWorkoutState.copy(
            searchedTerm = "",
            listOfSearchedWorkouts = libraryWorkoutState.masterWorkoutList
        )
    }
}
