package com.example.fitjournal.library.presentation.screen.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fitjournal.core.data.mockdata.MockData.libraryWorkoutList
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory
import com.example.fitjournal.library.presentation.screen.library.utils.mapToLibraryUiList
import com.example.fitjournal.library.presentation.screen.library.utils.searchForText
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

    init {
        // Dummy Data for now
        val workoutMap = libraryWorkoutList.groupBy { it.first() }.toSortedMap()
        val masterWorkoutList = mapToLibraryUiList(workoutMap)
        setMasterListOfWorkouts(masterWorkoutList)
    }

    private fun handleLibraryWorkoutEvents(event: LibraryWorkoutClickEvents) {
        when (event) {
            LibraryWorkoutClickEvents.ClearSearchBarText -> clearSearchBarText()
            is LibraryWorkoutClickEvents.UpdateSearchBarText -> {
                updateSearchBarText(event.text)
                updateSearchedWorkouts(event.text)
            }
        }
    }

    private fun updateSearchedWorkouts(text: String) {
        val filteredList = searchForText(text)
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

    private fun clearSearchBarText() {
        libraryWorkoutState = libraryWorkoutState.copy(
            searchedTerm = ""
        )
    }
}
