package com.example.fitjournal.screens.library

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import com.example.fitjournal.theme.Spacing

@Composable
fun LibraryScreen(modifier: Modifier) {
    var isTextFieldFocused by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxSize().padding(horizontal = Spacing.spacing16)) {
        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = if (isTextFieldFocused) MaterialTheme.colorScheme.primary else LocalContentColor.current
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary
            ),
            placeholder = {
                Text("Search for workout")
            },
            modifier = Modifier
                .padding(vertical = Spacing.spacing8)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Spacing.spacing8))
                .border(shape = RoundedCornerShape(Spacing.spacing8), border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = Spacing.spacing1))
                .onFocusChanged {
                    isTextFieldFocused = it.isFocused
                },
            shape = RoundedCornerShape(Spacing.spacing8)
        )
    }
}
