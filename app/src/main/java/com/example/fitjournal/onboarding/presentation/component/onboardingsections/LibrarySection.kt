package com.example.fitjournal.onboarding.presentation.component.onboardingsections

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
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.example.fitjournal.core.presentation.commoncomponents.listHeader.CategoryHeader
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.presentation.utils.determineFocusColor
import com.example.fitjournal.library.presentation.components.AddNewWorkoutText
import com.example.fitjournal.library.presentation.components.ExerciseItem
import com.example.fitjournal.onboarding.presentation.component.TypewriterText

@Composable
fun LibrarySection(
    navigateToStatistics: () -> Unit
) {
    val libraryText =
        "This is your Workout Library. Here You can create, edit, or delete workouts that can later be added to your journal. You need to have workouts in your library in order to add them to your journal."
    var showButton by rememberSaveable {
        mutableStateOf(true)
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
            exercise = "Squats",
            showEditLibraryWorkoutDialog = {},
            workoutOnClick = {},
            isBlurActive = false,
            removeBlur = {}
        )
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        TypewriterText(text = libraryText, onTextEffectComplete = { /*TODO*/ })
        Spacer(modifier = Modifier.height(Spacing.spacing32))
        if (showButton) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SaveButton(
                    text = "View Statistics",
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
    }
}
