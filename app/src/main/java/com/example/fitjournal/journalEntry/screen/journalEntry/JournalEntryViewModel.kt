package com.example.fitjournal.journalEntry.screen.journalEntry

import androidx.compose.runtime.Composable
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
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.journalEntry.model.JournalEntryUiModel
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory
import com.example.fitjournal.library.presentation.screen.library.utils.mapToLibraryUiList
import kotlinx.coroutines.launch
import javax.inject.Inject

class JournalEntryViewModel @Inject constructor(
    private val realmWorkoutLibraryUseCase: RealmWorkoutLibraryUseCase
) : ViewModel() {

    private val workoutList = MockData.mockLibraryList

    var selectedWorkoutDetail: MutableState<RealmWorkoutLibrary?> = mutableStateOf(null)
    var journalEntryState by mutableStateOf(
        JournalEntryUiModel(
            handleJournalEntryClickEvents = {}
        )
    )
        private set

    @Composable
    fun searchWorkout(searchValue: String): List<Pair<WorkoutTypeEnum, List<RealmWorkoutLibrary>>> {
        val listOfWeightLiftingWorkouts =
            workoutList.filter { it.type == WorkoutTypeEnum.WEIGHT_TRAINING.workoutTitle() }
        val listOfCardioWorkouts =
            workoutList.filter { it.type == WorkoutTypeEnum.CARDIO.workoutTitle() }
        val listOfCalisthenicsWorkouts =
            workoutList.filter { it.type == WorkoutTypeEnum.CALISTHENICS.workoutTitle() }

        return if (searchValue.isNotEmpty()) {
            workoutList.filter { it.name.contains(searchValue, ignoreCase = true) }
            listOf(
                Pair(
                    WorkoutTypeEnum.WEIGHT_TRAINING,
                    listOfWeightLiftingWorkouts.filter {
                        it.name.contains(
                            searchValue,
                            ignoreCase = true
                        )
                    }),
                Pair(
                    WorkoutTypeEnum.CARDIO,
                    listOfCardioWorkouts.filter {
                        it.name.contains(
                            searchValue,
                            ignoreCase = true
                        )
                    }),
                Pair(
                    WorkoutTypeEnum.CALISTHENICS,
                    listOfCalisthenicsWorkouts.filter {
                        it.name.contains(
                            searchValue,
                            ignoreCase = true
                        )
                    })
            )
        } else {
            listOf(
                Pair(WorkoutTypeEnum.WEIGHT_TRAINING, listOfWeightLiftingWorkouts),
                Pair(WorkoutTypeEnum.CARDIO, listOfCardioWorkouts),
                Pair(WorkoutTypeEnum.CALISTHENICS, listOfCalisthenicsWorkouts)
            )
        }
    }

    fun getDataFromRealmDb() {
        viewModelScope.launch {
            val workoutList = realmWorkoutLibraryUseCase.getRealmWorkoutLibraryList()
            if (workoutList.isNotEmpty()) {
                val libraryList = workoutList.groupBy { it.name.first().uppercase() }.toSortedMap()
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

    private fun searchForText(text: String, list: List<WorkoutCategory>): List<WorkoutCategory> {
        val filteredList = list.map { category ->
            WorkoutCategory(
                name = category.name,
                items =
                category.items.filter { workout ->
                    val lowercaseWorkout = workout.workoutName.lowercase()
                    lowercaseWorkout.contains(text.lowercase())
                }.toMutableStateList()
            )
        }
        return filteredList.filterNot { it.items.isEmpty() }
    }

    private fun updateJournalEntryState(newJournalEntryState: JournalEntryUiModel) {
        journalEntryState = newJournalEntryState
    }
}
