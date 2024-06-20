package com.alexafit.fitjournal.onboarding.presentation.component.onboardingsections

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.alexafit.fitjournal.core.presentation.theme.Spacing

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
            text = stringResource(id = R.string.title_onboarding_outro),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        Text(
            text = stringResource(id = R.string.subtitle_onboarding_outro_part_1),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        Text(
            text = stringResource(id = R.string.subtitle_onboarding_outro_part_2),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing64))
        SaveButton(
            text = stringResource(id = R.string.button_onboarding_outro),
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
