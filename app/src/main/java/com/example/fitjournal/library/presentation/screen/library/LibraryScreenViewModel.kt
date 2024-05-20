package com.example.fitjournal.library.presentation.screen.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
import com.example.fitjournal.core.util.filter.searchForText
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutItemDialogUiModel
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
            LibraryWorkoutClickEvents.ClearSearch -> {
                updateLibraryWorkoutState(
                    newLibraryWorkoutState = libraryWorkoutState.copy(
                        searchedTerm = "",
                        listOfSearchedWorkouts = libraryWorkoutState.masterWorkoutList.toMutableStateList()
                    )
                )
            }

            is LibraryWorkoutClickEvents.UpdateSearch -> {
                val filteredList = searchForText(
                    event.text,
                    libraryWorkoutState.masterWorkoutList
                )
                updateLibraryWorkoutState(
                    newLibraryWorkoutState = libraryWorkoutState.copy(
                        searchedTerm = event.text,
                        listOfSearchedWorkouts = filteredList.toMutableStateList()
                    )
                )
            }

            is LibraryWorkoutClickEvents.WorkoutItemClicked -> {
                updateLibraryWorkoutState(
                    newLibraryWorkoutState = libraryWorkoutState.copy(
                        workoutItemDialogUiModel = WorkoutItemDialogUiModel(
                            workoutTitle = event.workoutTitle
                        )
                    )
                )
            }
        }
    }

    private fun updateLibraryWorkoutState(newLibraryWorkoutState: LibraryWorkoutUiModel) {
        libraryWorkoutState = newLibraryWorkoutState
    }

    fun getDataFromRealmDb() {
        viewModelScope.launch {
            val workoutList = realmWorkoutLibraryUseCase.getRealmWorkoutLibraryList()
            if (workoutList.isNotEmpty()) {
                val libraryList = workoutList.groupBy { it.name.first().uppercase() }.toSortedMap()
                val masterWorkoutList = mapToLibraryUiList(libraryList)
                updateLibraryWorkoutState(
                    newLibraryWorkoutState = libraryWorkoutState.copy(
                        masterWorkoutList = masterWorkoutList,
                        listOfSearchedWorkouts = if (libraryWorkoutState.searchedTerm.isEmpty()) {
                            masterWorkoutList.toMutableStateList()
                        } else {
                            searchForText(
                                libraryWorkoutState.searchedTerm,
                                libraryWorkoutState.masterWorkoutList
                            ).toMutableStateList()
                        }
                    )
                )
            }
        }
    }
}
