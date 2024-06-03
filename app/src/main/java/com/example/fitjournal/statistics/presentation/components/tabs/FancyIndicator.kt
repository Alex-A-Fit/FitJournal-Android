package com.example.fitjournal.statistics.presentation.components.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun FancyIndicator(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(Spacing.spacing4)
            .background(Color.White, RoundedCornerShape(Spacing.spacing32))
            .zIndex(1f)
    )
}