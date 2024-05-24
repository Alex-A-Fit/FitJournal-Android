package com.example.fitjournal.library.presentation.screen.library.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents

@Composable
fun ExerciseItem(
    exercise: String,
    isBlurActive: Boolean,
    removeBlur: (Boolean) -> Unit,
    showEditLibraryWorkoutDialog: () -> Unit,
    workoutOnClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indicator = LocalIndication.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing12)
            .clickable(
                interactionSource = interactionSource,
                indication = if (isBlurActive) null else indicator
            ) {
                if (isBlurActive) {
                    removeBlur(true)
                } else {
                    workoutOnClick()
                    showEditLibraryWorkoutDialog()
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ExerciseName(exercise = exercise)
        Icon(
            painter = painterResource(id = R.drawable.ic_right_chevron),
            contentDescription = stringResource(id = R.string.content_desc_navigate_to_exercise_details_icon),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun ExerciseName(exercise: String) {
    Text(
        text = exercise,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyLarge
    )
}
