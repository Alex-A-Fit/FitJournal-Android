package com.example.fitjournal.statistics.presentation.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.data.mockdata.MockData
import com.example.fitjournal.core.domain.mapper.mapToWorkoutUiModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.usecase.realm.RealmUseCase
import com.example.fitjournal.core.presentation.model.WorkoutUiModel
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.statistics.domain.mapper.toRealmWorkoutEntry
import com.example.fitjournal.statistics.domain.model.StatisticsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// note: Using statistics screen to manual test crud functions
// until i can create junit tests for them in actual impl

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val realmUseCase: RealmUseCase
) : ViewModel() {
    var statisticsScreenState: StatisticsScreenState by mutableStateOf(StatisticsScreenState())
        private set

    fun addSingleObjectToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val didUpdateWork = realmUseCase.addSingleWorkoutEntryToRealmDbUseCase(
                realmWorkoutEntry = MockData.weightTraining1(org.mongodb.kbson.ObjectId())
            )
            Log.d("Realm Updates", "Realm Added new entry $didUpdateWork")
        }
    }

    fun getDataFromRealmDb() {
        viewModelScope.launch {
            val workoutList = realmUseCase.getRealmWorkoutEntryList()
            if (workoutList.isNotEmpty()) {
                updateStatisticsScreenState(
                    newStatisticsScreenState = statisticsScreenState.copy(
                        workoutModelList = workoutList,
                        workoutList = createWorkoutUiModel(listOfWorkouts = workoutList)
                    )
                )
            }
        }
    }

    private fun createWorkoutUiModel(listOfWorkouts: List<WorkoutModel>): UiState<List<WorkoutUiModel>> {
        if (listOfWorkouts.isEmpty()) return UiState.Empty
        val workoutsMapped = listOfWorkouts.map {
            it.mapToWorkoutUiModel()
        }
        return UiState.Success(workoutsMapped)
    }

    private fun updateStatisticsScreenState(newStatisticsScreenState: StatisticsScreenState) {
        statisticsScreenState = newStatisticsScreenState
    }

    fun clearUiState() {
        updateStatisticsScreenState(
            newStatisticsScreenState = statisticsScreenState.copy(
                workoutList = UiState.None
            )
        )
    }

    fun updateSingleObjectToDb(
        updatedItemIndex: Int,
        workoutType: String
    ) {
        if (statisticsScreenState.workoutModelList.isNotEmpty()) {
            val updatedRealmEntry =
                statisticsScreenState.workoutModelList[updatedItemIndex].toRealmWorkoutEntry(
                    workoutType
                )
            updatedRealmEntry.workout?.name = "Alex Is Awesome"
            viewModelScope.launch {
                val didUpdateWork = realmUseCase.updateSingleWorkoutEntryToRealmDbUseCase(
                    updatedRealmWorkoutEntry = updatedRealmEntry
                )
                if (didUpdateWork) {
                    getDataFromRealmDb()
                }
                Log.d("Realm Updates", "Realm Added new entry $didUpdateWork")
            }
        } else {
            Log.d("Realm Updates", "Realm did NOT update index $updatedItemIndex")
        }
    }

    fun deleteWorkoutEntry(
        getString: (Int) -> String
    ) {
        if (statisticsScreenState.workoutModelList.isNotEmpty()) {
            val workoutEntryToBeDeleted = statisticsScreenState.workoutModelList[0]
            val realmEntryToDelete = workoutEntryToBeDeleted.toRealmWorkoutEntry(
                workoutType = getString(workoutEntryToBeDeleted.workoutDetailsModel.workoutTypeEnum.stringId)
            )
            viewModelScope.launch {
                val wasDeleteSuccessful = realmUseCase.deleteWorkoutEntryFromRealmDbUseCase(
                    realmWorkoutEntry = realmEntryToDelete
                )
                if (wasDeleteSuccessful) {
                    getDataFromRealmDb()
                }
                Log.d("Realm Updates", "Realm entry $realmEntryToDelete was deleted")
            }
        } else {
            Log.d("Realm Updates", "Realm did NOT delete realm entry")
        }
    }
}
