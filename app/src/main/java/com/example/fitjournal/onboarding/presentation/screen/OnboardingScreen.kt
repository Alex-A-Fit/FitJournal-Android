package com.example.fitjournal.onboarding.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.presentation.theme.White
import com.example.fitjournal.onboarding.presentation.component.carouselcircles.CarouselCircles
import com.example.fitjournal.onboarding.presentation.component.onboardingsections.IntroSection
import com.example.fitjournal.onboarding.presentation.component.onboardingsections.JournalSection
import com.example.fitjournal.onboarding.presentation.component.onboardingsections.LibrarySection
import com.example.fitjournal.onboarding.presentation.component.onboardingsections.OutroSection
import com.example.fitjournal.onboarding.presentation.component.onboardingsections.StatisticsSection
import com.example.fitjournal.onboarding.presentation.model.OnboardingSections

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    setUserCompletedOnboarding: () -> Unit,
    onboardSectionToDisplay: OnboardingSections,
    navigateOnboarding: (OnboardingSections) -> Unit,
    bottomBarVisibility: (Boolean) -> Unit
) {
    val gradient = Brush.linearGradient(
        0.0f to MaterialTheme.colorScheme.primary,
        0.9f to White,
        1.0f to White,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Box(
        modifier = if (onboardSectionToDisplay == OnboardingSections.Intro || onboardSectionToDisplay == OnboardingSections.End) {
            modifier
                .background(gradient)
                .fillMaxSize()
        } else {
            modifier
        }
    ) {
        when (onboardSectionToDisplay) {
            OnboardingSections.Intro -> {
                LaunchedEffect(key1 = onboardSectionToDisplay) {
                    bottomBarVisibility(false)
                }
                IntroSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.spacing32),
                    navigateOnboarding = navigateOnboarding
                )
            }

            OnboardingSections.JournalSection -> {
                LaunchedEffect(key1 = onboardSectionToDisplay) {
                    bottomBarVisibility(true)
                }
                JournalSection(
                    modifier = Modifier
                        .fillMaxSize(),
                    navigateToLibrary = {
                        navigateOnboarding(OnboardingSections.LibrarySection)
                    }
                )
            }

            OnboardingSections.LibrarySection -> {
                LibrarySection(
                    navigateToStatistics = {
                        navigateOnboarding(OnboardingSections.StatsSection)
                    }
                )
            }

            OnboardingSections.StatsSection -> {
                StatisticsSection(
                    modifier = Modifier.fillMaxSize(),
                    navigateToEndOfTutorial = {
                        navigateOnboarding(OnboardingSections.End)
                    }
                )
            }

            OnboardingSections.End -> {
                LaunchedEffect(key1 = onboardSectionToDisplay) {
                    bottomBarVisibility(false)
                }
                OutroSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.spacing32),
                    setUserCompletedOnboarding = setUserCompletedOnboarding
                )
            }
        }
        if (onboardSectionToDisplay == OnboardingSections.Intro || onboardSectionToDisplay == OnboardingSections.End) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                CarouselCircles(
                    currentOnboardingSection = onboardSectionToDisplay,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Spacing.spacing64))
            }
        }
    }
}
