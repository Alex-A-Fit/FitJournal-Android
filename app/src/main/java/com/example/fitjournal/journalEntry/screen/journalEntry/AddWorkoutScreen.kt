package com.example.fitjournal.journalEntry.screen.journalEntry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.fitjournal.core.presentation.commoncomponents.textField.SearchBar
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.journalEntry.components.JournalEntryList
import com.example.fitjournal.journalEntry.model.JournalEntryUiModel
import com.example.fitjournal.journalEntry.model.events.JournalEntryEvents

@Composable
fun AddWorkoutScreen(
    modifier: Modifier,
    journalEntryState: JournalEntryUiModel,
    navigateToDestination: (NavigationInterface) -> Unit
) {
    val searchText = rememberSaveable(journalEntryState.searchedTerm) {
        mutableStateOf(journalEntryState.searchedTerm)
    }
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
            }
    ) {
        SearchBar(
            searchedTerm = searchText.value,
            updateSearch = { searchedText ->
                journalEntryState.handleJournalEntryClickEvents(
                    JournalEntryEvents.FilterSearchByWorkout(
                        searchedText
                    )
                )
            },
            clearSearch = {
                journalEntryState.handleJournalEntryClickEvents(JournalEntryEvents.ClearSearchBarFilter)
            },
            keyboardController = keyboardController,
            focusManager = focusManager
        )
        JournalEntryList(
            workoutList = journalEntryState.listOfSearchedWorkouts,
            selectedWorkout = { name, type ->
                navigateToDestination(NavigationInterface.NavigateToJournalEntryDetails(name, type))
            }
        )
    }
}
