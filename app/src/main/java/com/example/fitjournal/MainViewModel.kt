package com.example.fitjournal

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase
) : ViewModel() {

    var appScreenState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
        private set

    // For now until we create check for fetching realm,
    // flip boolean to true to create mock data and fetch from realm
    // flip boolean to false to ONLY fetch mock data from realm
    init {
        val createMockData = false
        viewModelScope.launch(Dispatchers.IO) {
            if (createMockData) {
                realmWorkoutEntryUseCase.createMockDataOfRealmWorkoutEntryUseCase()
            }
        }
    }

    fun runSplashScreen() {
        viewModelScope.launch {
            delay(2000L)
            appScreenState = MainActivityUiState.Success
        }
    }

    fun addWorkoutToDatabase(
        workoutName: String,
        workoutTypeEnum: WorkoutTypeEnum,
        successCallback: suspend () -> Unit
    ) {
        Log.d("addWorkout", "Workout Name: $workoutName, WorkoutTypeEnum: $workoutTypeEnum")
        viewModelScope.launch {
            successCallback()
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data object Success : MainActivityUiState
}
