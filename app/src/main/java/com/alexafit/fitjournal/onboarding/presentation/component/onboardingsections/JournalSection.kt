package com.alexafit.fitjournal.onboarding.presentation.component.onboardingsections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.AddToJournalButton
import com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.home.presentation.components.card.WeightLiftingCard
import com.alexafit.fitjournal.home.presentation.model.ui.WeightLiftingUi
import com.alexafit.fitjournal.onboarding.presentation.component.TypewriterText
import com.alexafit.fitjournal.onboarding.presentation.component.carouselcircles.CarouselCircles
import com.alexafit.fitjournal.onboarding.presentation.model.OnboardingSections

@Composable
fun JournalSection(
    modifier: Modifier = Modifier,
    navigateToLibrary: () -> Unit
) {
    var showButton by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeightLiftingCard(
            weightLiftingUi = WeightLiftingUi(
                reps = 6,
                sets = 2,
                weight = 100.0,
                weightInKgs = 220.0,
                name = stringResource(id = R.string.text_squats),
                icon = R.drawable.icon_dumbell
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing16)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        AddToJournalButton(
            navigate = {},
            isEnabled = false
        )
        TypewriterText(
            text = stringResource(id = R.string.text_onboarding_typewriter_text_journal),
            onTextEffectComplete = {
                showButton = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing16)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing16))
        if (showButton) {
            SaveButton(
                text = stringResource(id = R.string.button_view_library),
                textModifier = Modifier.padding(
                    horizontal = Spacing.spacing32,
                    vertical = Spacing.spacing4
                ),
                textStyle = MaterialTheme.typography.headlineMedium
            ) {
                navigateToLibrary()
            }
        }
        Spacer(modifier = Modifier.height(Spacing.spacing48))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CarouselCircles(
                currentOnboardingSection = OnboardingSections.JournalSection,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing64))
        }
    }
}
