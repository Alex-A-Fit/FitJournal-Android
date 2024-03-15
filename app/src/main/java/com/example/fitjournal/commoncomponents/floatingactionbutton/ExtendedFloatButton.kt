package com.example.fitjournal.commoncomponents.floatingactionbutton

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExtendedFloatButton(
    modifier: Modifier = Modifier,
    onEvent: () -> Unit,
    iconAndTextComposable: @Composable () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onEvent,
        modifier = modifier,
        shape = RoundedCornerShape(50),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        iconAndTextComposable()
    }
}
