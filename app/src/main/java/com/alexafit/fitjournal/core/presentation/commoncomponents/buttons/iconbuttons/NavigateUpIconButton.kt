package com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun NavigateUpIconButton(
    navigateUp: () -> Unit
) {
    IconButton(onClick = navigateUp) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
}
