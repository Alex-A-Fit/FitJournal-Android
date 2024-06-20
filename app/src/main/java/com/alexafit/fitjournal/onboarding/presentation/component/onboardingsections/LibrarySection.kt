package com.alexafit.fitjournal.onboarding.presentation.component.onboardingsections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.alexafit.fitjournal.core.presentation.commoncomponents.listHeader.CategoryHeader
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.core.presentation.utils.determineFocusColor
import com.alexafit.fitjournal.library.presentation.components.AddNewWorkoutText
import com.alexafit.fitjournal.library.presentation.components.ExerciseItem
import com.alexafit.fitjournal.onboarding.presentation.component.TypewriterText
import com.alexafit.fitjournal.onboarding.presentation.component.carouselcircles.CarouselCircles
import com.alexafit.fitjournal.onboarding.presentation.model.OnboardingSections

@Composable
fun LibrarySection(
    navigateToStatistics: () -> Unit
) {
    var showButton by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.spacing16)
    ) {
        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.content_desc_search_icon)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary
            ),
            placeholder = {
                Text(stringResource(id = R.string.text_library_searchbar_placeholder))
            },
            modifier = Modifier
                .padding(vertical = Spacing.spacing8)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Spacing.spacing8))
                .border(
                    shape = RoundedCornerShape(Spacing.spacing8),
                    border = BorderStroke(
                        color = determineFocusColor(isFocused = false),
                        width = Spacing.spacing1
                    )
                ),
            shape = RoundedCornerShape(Spacing.spacing8),
            enabled = false
        )
        AddNewWorkoutText(
            modifier = Modifier.padding(vertical = Spacing.spacing8),
            onClick = {}
        )
        CategoryHeader(text = "S")
        ExerciseItem(
            exercise = stringResource(id = R.string.text_squats),
            showEditLibraryWorkoutDialog = {},
            workoutOnClick = {},
            isBlurActive = false,
            removeBlur = {}
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        TypewriterText(
            text = stringResource(id = R.string.text_onboarding_typewriter_text_library),
            onTextEffectComplete = { showButton = true }
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        if (showButton) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SaveButton(
                    text = stringResource(id = R.string.button_view_statistics),
                    textModifier = Modifier.padding(
                        horizontal = Spacing.spacing32,
                        vertical = Spacing.spacing4
                    ),
                    textStyle = MaterialTheme.typography.headlineMedium
                ) {
                    navigateToStatistics()
                }
            }
        }
        Spacer(modifier = Modifier.height(Spacing.spacing48))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CarouselCircles(
                currentOnboardingSection = OnboardingSections.LibrarySection,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing64))
        }
    }
}
