package com.example.fitjournal.library.presentation.screen.library

import LibraryListSection
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.fitjournal.core.presentation.commoncomponents.textField.SearchBar
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel

@Composable
fun LibraryScreen(
    modifier: Modifier,
    libraryWorkoutState: LibraryWorkoutUiModel,
    isBlurActive: Boolean,
    removeBlur: (Boolean) -> Unit,
    libraryScreenListState: LazyListState
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.spacing16)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                keyboardController?.hide()
                focusManager.clearFocus(true)
                removeBlur(false)
            }
    ) {
        SearchBar(
            searchedTerm = libraryWorkoutState.searchedTerm,
            updateSearchBarText = { searchedText ->
                libraryWorkoutState.handleLibraryWorkoutClickEvents(
                    LibraryWorkoutClickEvents.UpdateSearchBarText(searchedText)
                )
            },
            clearSearch = {
                libraryWorkoutState.handleLibraryWorkoutClickEvents(
                    LibraryWorkoutClickEvents.ClearSearchBarText
                )
            },
            keyboardController = keyboardController,
            focusManager = focusManager
        )
        LibraryListSection(
            categories = libraryWorkoutState.listOfSearchedWorkouts,
            isBlurActive = isBlurActive,
            libraryScreenListState = libraryScreenListState
        )
    }
}
