package com.example.fitjournal.library.presentation.screen.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.fitjournal.theme.Spacing

@Composable
fun ExerciseItem(
    exercise: String,
    iconId: Int? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.spacing8),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = exercise)
        iconId?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = "Navigate to exercise details"
            )
        }
    }
}
