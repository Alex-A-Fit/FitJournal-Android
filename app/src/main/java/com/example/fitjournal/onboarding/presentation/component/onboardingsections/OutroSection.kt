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

@Composable
fun OutroSection(
    modifier: Modifier = Modifier,
    setUserCompletedOnboarding: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacing24))
        Text(
            text = "There You have it!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        Text(
            text = "Fit Journal is meant to help you track your day to day workouts in a lightweight, easy to use app.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        Text(
            text = "If you ever need to see this again, Just click the question mark at the top right of your journal!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        SaveButton(
            text = "Proceed to Fit Journal",
            textModifier = Modifier.padding(
                horizontal = Spacing.spacing32,
                vertical = Spacing.spacing4
            ),
            textStyle = MaterialTheme.typography.headlineMedium
        ) {
            setUserCompletedOnboarding()
        }
    }
}
