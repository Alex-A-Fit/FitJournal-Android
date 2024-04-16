package com.example.fitjournal.library.presentation.screen.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun CategoryHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.linearGradient(
        0.0f to colorScheme.primary,
        1.0f to colorScheme.inversePrimary,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Column(
        modifier = modifier.clip(RoundedCornerShape(Spacing.spacing8))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            modifier = modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(start = Spacing.spacing16)
        )
    }
}
