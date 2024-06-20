package com.alexafit.fitjournal.onboarding.presentation.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexafit.fitjournal.core.data.repository.OnboardingTutorialRepositoryImpl
import com.alexafit.fitjournal.onboarding.presentation.model.OnboardingSections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingTutorialRepository: OnboardingTutorialRepositoryImpl
) : ViewModel() {
    val onboardingSection: MutableState<OnboardingSections> =
        mutableStateOf(OnboardingSections.Intro)
    private val hasUserSeenTutorial: MutableStateFlow<Boolean> = MutableStateFlow(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            onboardingTutorialRepository.getHasUserSeenTutorial().collectLatest {
                val hasUserSeenTutorialBefore = it ?: false
                hasUserSeenTutorial.value = hasUserSeenTutorialBefore
            }
        }
    }

    fun setUserHasOnboarded() {
        if (!hasUserSeenTutorial.value) {
            viewModelScope.launch {
                onboardingTutorialRepository.setUserHasSeenTutorial()
                hasUserSeenTutorial.value = true
            }
        }
    }

    fun setOnboardingSection(section: OnboardingSections) {
        onboardingSection.value = section
    }
}
