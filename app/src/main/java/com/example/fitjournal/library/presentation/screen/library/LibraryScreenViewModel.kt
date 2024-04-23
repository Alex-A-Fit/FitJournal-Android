package com.example.fitjournal.library.presentation.screen.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory
import com.example.fitjournal.library.presentation.screen.library.utils.mapToLibraryUiList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryScreenViewModel @Inject constructor(
    private val realmWorkoutLibraryUseCase: RealmWorkoutLibraryUseCase
) : ViewModel() {
    var libraryWorkoutState by mutableStateOf(
        LibraryWorkoutUiModel(
            handleLibraryWorkoutClickEvents = ::handleLibraryWorkoutEvents
        )
    )
        private set

    init {
        getDataFromRealmDb()
    }

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
        val filteredList = searchForText(text, libraryWorkoutState.masterWorkoutList)
        setListOfSearchedWorkouts(filteredList)
    }

    private fun searchForText(text: String, list: List<WorkoutCategory>): List<WorkoutCategory> {
        return list.filter { category ->
            category.items.any { workout ->
                workout.workoutName.lowercase().contains(text.lowercase())
            }
        }
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

    private fun getDataFromRealmDb() {
        viewModelScope.launch {
            val workoutList = realmWorkoutLibraryUseCase.getRealmWorkoutLibraryList()
            if (workoutList.isNotEmpty()) {
                val libraryList = workoutList.groupBy { it.name.first() }.toSortedMap()
                val masterWorkoutList = mapToLibraryUiList(libraryList)
                setMasterListOfWorkouts(masterWorkoutList)
            }
        }
    }
}
