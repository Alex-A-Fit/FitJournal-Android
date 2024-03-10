package com.example.fitjournal.home.presentation.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.theme.Spacing

@Composable
fun ExerciseTypeIcon(workoutIcon: Int) {
    Icon(
        painter = painterResource(id = workoutIcon),
        contentDescription = stringResource(id = R.string.content_desc_exercise_icon),
        modifier = Modifier
            .size(Spacing.spacing32)
            .clip(RoundedCornerShape(Spacing.spacing16))
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(Spacing.spacing16)
            ),
        tint = MaterialTheme.colorScheme.onPrimary
    )
}
