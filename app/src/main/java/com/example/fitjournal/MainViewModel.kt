package com.example.fitjournal

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.home.presentation.model.enum.WorkoutTypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var appScreenState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
        private set

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
