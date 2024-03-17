package com.example.fitjournal.library.presentation.screen.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutItem
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel
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
        val workoutList = listOf("Bench", "Squats", "Lateral Raises", "Elevated Goblet Squats")
        val masterWorkoutList = workoutList.map { workout ->
            LibraryWorkoutItem(
                workoutName = workout,
                workoutDetails = "Details for $workout go here"
            )
        }
        setMasterListOfWorkouts(masterWorkoutList)
    }

    private fun handleLibraryWorkoutEvents(event: LibraryWorkoutClickEvents) {
        when (event) {
            LibraryWorkoutClickEvents.ClearSearchBarText -> clearSearchBarText()
            is LibraryWorkoutClickEvents.UpdateSearchBarText -> updateSearchBarText(event.text)
        }
    }
    private fun setMasterListOfWorkouts(workoutList: List<LibraryWorkoutItem>) {
        libraryWorkoutState = libraryWorkoutState.copy(
            masterWorkoutList = workoutList,
            listOfSearchedWorkouts = workoutList
        )
    }

    private fun updateSearchBarText(searchedText: String) {
        libraryWorkoutState = libraryWorkoutState.copy(
            searchedTerm = searchedText
        )
    }

    fun clearSearchBarText() {
        libraryWorkoutState = libraryWorkoutState.copy(
            searchedTerm = ""
        )
    }
}
