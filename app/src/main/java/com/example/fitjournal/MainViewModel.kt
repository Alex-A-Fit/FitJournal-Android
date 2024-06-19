package com.example.fitjournal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.data.repository.OnboardingTutorialRepositoryImpl
import com.example.fitjournal.core.domain.model.WorkoutLibraryModel
import com.example.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val realmWorkoutLibraryUseCase: RealmWorkoutLibraryUseCase,
    private val onboardingTutorialRepository: OnboardingTutorialRepositoryImpl
) : ViewModel() {

    val isThisUserFirstTimeUsingApp: MutableStateFlow<Boolean> = MutableStateFlow(true)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            onboardingTutorialRepository.getHasUserSeenTutorial().collectLatest {
                val hasUserSeenTutorial = it ?: false
                if (!hasUserSeenTutorial) {
                    realmWorkoutLibraryUseCase.createMockDataOfRealmWorkoutLibraryUseCase()
                }
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
            val wasLibraryItemAdded =
                realmWorkoutLibraryUseCase.addSingleLibraryItemToRealmDbUseCase(
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
