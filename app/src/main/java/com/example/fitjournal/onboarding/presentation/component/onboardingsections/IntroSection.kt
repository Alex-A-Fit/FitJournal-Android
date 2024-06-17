package com.example.fitjournal.onboarding.presentation.component.onboardingsections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.onboarding.presentation.model.OnboardingSections

@Composable
fun IntroSection(
    modifier: Modifier = Modifier,
    navigateOnboarding: (OnboardingSections) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacing64))
        Text(
            text = "Welcome to Fit Journal!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(Spacing.spacing24))
        Text(
            text = "Let's quickly go through how to track your workouts and crush your goals.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        Text(
            text = "Click 'Start' to begin!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        SaveButton(
            text = "Start",
            textModifier = Modifier.padding(
                horizontal = Spacing.spacing32,
                vertical = Spacing.spacing4
            ),
            textStyle = MaterialTheme.typography.headlineMedium
        ) {
            navigateOnboarding(OnboardingSections.JournalSection)
        }
    }
}
