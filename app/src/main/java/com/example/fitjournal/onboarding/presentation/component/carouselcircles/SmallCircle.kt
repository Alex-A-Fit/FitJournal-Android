package com.example.fitjournal.onboarding.presentation.component.carouselcircles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun SmallCircle(
    shouldButtonBeHighlighted: Boolean
) {
    Box(
        modifier = Modifier
            .size(Spacing.spacing16)
            .clip(RoundedCornerShape(percent = 100))
            .background(
                color = if (shouldButtonBeHighlighted) MaterialTheme.colorScheme.primary else Color.LightGray,
                RoundedCornerShape(percent = 100)
            )
    )
    Spacer(modifier = Modifier.width(Spacing.spacing8))
}
