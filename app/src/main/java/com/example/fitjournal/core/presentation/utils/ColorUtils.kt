package com.example.fitjournal.core.presentation.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun determineFocusColor(isFocused: Boolean): Color =
    if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
