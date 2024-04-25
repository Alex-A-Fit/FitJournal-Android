package com.example.fitjournal.home.presentation.screen.editworkout

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.home.presentation.model.state.EditWorkoutUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditWorkoutViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase
) : ViewModel() {
    var editWorkoutState: EditWorkoutUiState by mutableStateOf(
        EditWorkoutUiState()
    )
        private set

    fun getSingleWorkout(workoutId: String?) {
        if (workoutId.isNullOrEmpty()) return
        try {
            viewModelScope.launch {
                val workout = realmWorkoutEntryUseCase.getSingleRealmWorkoutEntry(workoutId)
                editWorkoutState = editWorkoutState.copy(
                    workout = workout
                )
            }
        } catch (e: IllegalStateException) {
            Unit
        } catch (e: Exception) {
            Unit
        }
    }
}
