package com.example.fitjournal.jouranlEntry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitjournal.core.domain.model.WorkoutDetail
import com.example.fitjournal.core.presentation.commoncomponents.textField.SearchBar
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.jouranlEntry.components.JournalEntryList
import com.example.fitjournal.jouranlEntry.domain.JournalEntryViewModel

@Composable
fun JournalEntryScreen(
    modifier: Modifier,
    selectedJournalEntry: (WorkoutDetail) -> Unit,
    navigateToDestination: (NavigationInterface) -> Unit
) {

    val viewModel: JournalEntryViewModel = viewModel()
    val searchText = remember { mutableStateOf("") }
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
            updateSearchBarText = { searchedText ->
                searchText.value = searchedText
            },
            keyboardController = keyboardController,
            focusManager = focusManager
        )
        JournalEntryList(viewModel.searchWorkout(searchText.value)) {
            selectedJournalEntry.invoke(it)
            navigateToDestination.invoke(NavigationInterface.NavigateToJournalEntryDetails)
        }
    }
}