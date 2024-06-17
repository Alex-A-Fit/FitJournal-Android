package com.example.fitjournal.onboarding.presentation.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.core.data.repository.OnboardingTutorialRepositoryImpl
import com.example.fitjournal.onboarding.presentation.model.OnboardingSections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingTutorialRepository: OnboardingTutorialRepositoryImpl
) : ViewModel() {
    val onboardingSection: MutableState<OnboardingSections> = mutableStateOf(OnboardingSections.Intro)
    fun setUserHasOnboarded() {
        viewModelScope.launch {
            onboardingTutorialRepository.setUserHasSeenTutorial()
        }
    }
    fun setOnboardingSection(section: OnboardingSections) {
        onboardingSection.value = section
    }
}
