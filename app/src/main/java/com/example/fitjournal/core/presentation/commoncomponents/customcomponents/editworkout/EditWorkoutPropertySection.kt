package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.theme.MediumGray
import com.example.fitjournal.core.presentation.theme.Spacing

// basic composable for creating various editable workout fields
// pass in your own field for each workout property
// thereby sectioning it out
@Composable
fun EditWorkoutPropertySection(
    workoutProperty: String,
    textField: @Composable () -> Unit
) {
    Spacer(modifier = Modifier.height(Spacing.spacing16))
    WorkoutPropertyText(workoutProperty = workoutProperty)
    Spacer(modifier = Modifier.height(Spacing.spacing8))
    textField()
}

@Composable
fun WorkoutPropertyText(
    workoutProperty: String
) {
    Text(
        text = "$workoutProperty:",
        style = MaterialTheme
            .typography
            .titleMedium.copy(
                color = MaterialTheme.colorScheme.onTertiary
            ),
        color = MediumGray
    )

    HorizontalDivider(
        thickness = Spacing.spacing2,
        color = MaterialTheme.colorScheme.tertiary
    )
}
