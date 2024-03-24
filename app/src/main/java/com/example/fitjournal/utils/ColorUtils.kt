package com.example.fitjournal.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.fitjournal.theme.DefaultUnfocusedColor

@Composable
fun determineFocusColor(isFocused: Boolean): Color =
    if (isFocused) MaterialTheme.colorScheme.primary else DefaultUnfocusedColor
