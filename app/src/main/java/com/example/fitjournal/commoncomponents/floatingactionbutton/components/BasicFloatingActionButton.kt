package com.example.fitjournal.commoncomponents.floatingactionbutton.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BasicFloatingActionButton(
    modifier: Modifier = Modifier,
    onEvent: () -> Unit,
    icon: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick = { onEvent() },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
    ) {
        icon()
    }
}
