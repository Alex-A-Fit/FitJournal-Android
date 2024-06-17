package com.example.fitjournal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.data.repository.OnboardingTutorialRepositoryImpl
import com.example.fitjournal.core.domain.model.WorkoutLibraryModel
import com.example.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val realmWorkoutEntryUseCase: RealmWorkoutEntryUseCase,
    private val realmWorkoutLibraryUseCase: RealmWorkoutLibraryUseCase,
    private val onboardingTutorialRepository: OnboardingTutorialRepositoryImpl
) : ViewModel() {

    val isThisUserFirstTimeUsingApp: MutableStateFlow<Boolean> = MutableStateFlow(true)

    // For now until we create check for fetching realm,
    // flip boolean to true to create mock data and fetch from realm
    // flip boolean to false to ONLY fetch mock data from realm
    init {
        val createMockData = false

        viewModelScope.launch(Dispatchers.IO) {
            if (createMockData) {
                realmWorkoutEntryUseCase.createMockDataOfRealmWorkoutEntryUseCase()
                realmWorkoutLibraryUseCase.createMockDataOfRealmWorkoutLibraryUseCase()
            }
        }
        viewModelScope.launch {
            onboardingTutorialRepository.getHasUserSeenTutorial().collectLatest {
                val hasUserSeenTutorial = it ?: false
                isThisUserFirstTimeUsingApp.value = !hasUserSeenTutorial
            }
        }
    }

    fun getDataFromRealm(
        getDataFromRealmForHomeScreen: () -> Unit
    ) {
        getDataFromRealmForHomeScreen()
    }

    fun addWorkoutToLibraryDatabase(
        workoutName: String,
        workoutType: String,
        workoutTypeEnum: WorkoutTypeEnum,
        successCallback: suspend () -> Unit,
        errorCallback: suspend () -> Unit
    ) {
        viewModelScope.launch {
            val wasLibraryItemAdded = realmWorkoutLibraryUseCase.addSingleLibraryItemToRealmDbUseCase(
                workoutLibraryModel = WorkoutLibraryModel(
                    name = workoutName,
                    workoutType = workoutType,
                    workoutTypeEnum = workoutTypeEnum
                )
            )
            if (wasLibraryItemAdded) {
                successCallback()
            } else {
                errorCallback()
            }
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data object Success : MainActivityUiState
}
