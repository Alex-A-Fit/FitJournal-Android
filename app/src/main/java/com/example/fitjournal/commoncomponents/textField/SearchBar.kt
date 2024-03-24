package com.example.fitjournal.commoncomponents.textField

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.ImeAction
import com.example.fitjournal.R
import com.example.fitjournal.theme.Spacing
import com.example.fitjournal.utils.determineFocusColor

@Composable
fun SearchBar(
    searchedTerm: String,
    updateSearchBarText: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    var isTextFieldFocused by remember { mutableStateOf(false) }
    TextField(
        value = searchedTerm,
        onValueChange = { searchBarText ->
            updateSearchBarText(
                searchBarText
            )
        },
        keyboardActions = KeyboardActions(onDone = {
            isTextFieldFocused = false
            keyboardController?.hide()
            focusManager.clearFocus(true)
        }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = determineFocusColor(isFocused = isTextFieldFocused)
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
                    color = determineFocusColor(isFocused = isTextFieldFocused),
                    width = Spacing.spacing1
                )
            )
            .onFocusChanged {
                isTextFieldFocused = it.isFocused
            },
        shape = RoundedCornerShape(Spacing.spacing8)
    )
}
