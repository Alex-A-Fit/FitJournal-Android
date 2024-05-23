package com.example.fitjournal.addWorkout.screen.addworkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.fitjournal.addWorkout.components.UserWorkoutList
import com.example.fitjournal.addWorkout.model.AddWorkoutUiModel
import com.example.fitjournal.addWorkout.model.events.AddWorkoutEvents
import com.example.fitjournal.core.presentation.commoncomponents.textField.SearchBar
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun AddWorkoutScreen(
    modifier: Modifier,
    addWorkoutUiState: AddWorkoutUiModel,
    workoutDate: String,
    navigateToDestination: (NavigationInterface) -> Unit
) {
    val searchText = rememberSaveable(addWorkoutUiState.searchedTerm) {
        mutableStateOf(addWorkoutUiState.searchedTerm)
    }
    val date by rememberSaveable(workoutDate) {
        mutableStateOf(workoutDate)
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
                addWorkoutUiState.handleJournalEntryClickEvents(
                    AddWorkoutEvents.FilterSearchByWorkout(
                        searchedText
                    )
                )
            },
            clearSearch = {
                addWorkoutUiState.handleJournalEntryClickEvents(AddWorkoutEvents.ClearSearchBarFilter)
            },
            keyboardController = keyboardController,
            focusManager = focusManager
        )
        UserWorkoutList(
            workoutList = addWorkoutUiState.listOfSearchedWorkouts,
            selectedWorkout = { name, type ->
                navigateToDestination(NavigationInterface.NavigateToAddWorkoutDetails(name, type, date))
            }
        )
    }
}
