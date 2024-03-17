package com.example.fitjournal.library.presentation.screen.library.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel
import com.example.fitjournal.theme.Spacing

@Composable
fun LibrarySearchBar(
    libraryWorkoutState: LibraryWorkoutUiModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    var isTextFieldFocused by remember { mutableStateOf(false) }
    Box() {
        TextField(
            value = libraryWorkoutState.searchedTerm,
            onValueChange = { searchBarText ->
                if (searchBarText.lastOrNull() == '\n') {
                    isTextFieldFocused = false
                    keyboardController?.hide()
                    focusManager.clearFocus(true)
                } else {
                    libraryWorkoutState.handleLibraryWorkoutClickEvents(
                        LibraryWorkoutClickEvents.UpdateSearchBarText(searchBarText)
                    )
                }
            },
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
                Text(stringResource(id = R.string.text_library_searchbar_placeholder))
            },
            modifier = Modifier
                .padding(vertical = Spacing.spacing8)
                .fillMaxWidth()
                .clip(RoundedCornerShape(Spacing.spacing8))
                .border(
                    shape = RoundedCornerShape(Spacing.spacing8),
                    border = BorderStroke(
                        color = MaterialTheme.colorScheme.primary,
                        width = Spacing.spacing1
                    )
                )
                .onFocusChanged {
                    isTextFieldFocused = it.isFocused
                },
            shape = RoundedCornerShape(Spacing.spacing8)
        )
    }
}
