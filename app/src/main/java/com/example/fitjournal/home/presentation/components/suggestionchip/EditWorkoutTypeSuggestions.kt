package com.example.fitjournal.home.presentation.components.suggestionchip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ChipColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.theme.Spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkoutTypeSuggestions(
    workoutTypeChosen: WorkoutTypeEnum,
    modifier: Modifier = Modifier
) {
    val chipColorsSelectedChip = ChipColors(
        containerColor = MaterialTheme.colorScheme.primary,
        labelColor = MaterialTheme.colorScheme.onPrimary,
        leadingIconContentColor = MaterialTheme.colorScheme.onPrimary,
        trailingIconContentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.onTertiary,
        disabledLabelColor = MaterialTheme.colorScheme.tertiary,
        disabledLeadingIconContentColor = MaterialTheme.colorScheme.onTertiary,
        disabledTrailingIconContentColor = MaterialTheme.colorScheme.onTertiary
    )
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        SuggestionChip(
            onClick = {},
            label = { Text(text = stringResource(id = workoutTypeChosen.stringId)) },
            shape = RoundedCornerShape(Spacing.spacing16),
            colors = chipColorsSelectedChip
        )
    }
}
