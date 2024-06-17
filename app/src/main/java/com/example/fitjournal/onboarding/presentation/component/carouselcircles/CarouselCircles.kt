package com.example.fitjournal.onboarding.presentation.component.carouselcircles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fitjournal.onboarding.presentation.model.OnboardingSections

@Composable
fun CarouselCircles(
    modifier: Modifier = Modifier,
    currentOnboardingSection: OnboardingSections
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SmallCircle(shouldButtonBeHighlighted = currentOnboardingSection == OnboardingSections.Intro)
        SmallCircle(shouldButtonBeHighlighted = currentOnboardingSection == OnboardingSections.JournalSection)
        SmallCircle(shouldButtonBeHighlighted = currentOnboardingSection == OnboardingSections.LibrarySection)
        SmallCircle(shouldButtonBeHighlighted = currentOnboardingSection == OnboardingSections.StatsSection)
        SmallCircle(shouldButtonBeHighlighted = currentOnboardingSection == OnboardingSections.End)
    }
}
