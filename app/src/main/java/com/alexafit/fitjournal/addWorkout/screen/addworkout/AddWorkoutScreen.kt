package com.alexafit.fitjournal.addWorkout.screen.addworkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.addWorkout.components.AddWorkoutNoneScreen
import com.alexafit.fitjournal.addWorkout.components.UserWorkoutList
import com.alexafit.fitjournal.addWorkout.model.AddWorkoutUiModel
import com.alexafit.fitjournal.addWorkout.model.events.AddWorkoutEvents
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.AddWorkoutToLibraryDialog
import com.alexafit.fitjournal.core.presentation.commoncomponents.textField.SearchBar
import com.alexafit.fitjournal.core.presentation.navigation.NavigationDirectionInterface
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.library.domain.model.AddWorkoutToLibraryModel

@Composable
fun AddWorkoutScreen(
    modifier: Modifier,
    addWorkoutUiState: AddWorkoutUiModel,
    workoutDate: String,
    showSnackBar: suspend (String) -> Unit,
    navigateToDestination: (NavigationDirectionInterface) -> Unit
) {
    LaunchedEffect(key1 = true) {
        addWorkoutUiState.handleAddWorkoutClickEvents(AddWorkoutEvents.SyncRealmWorkoutEntryFromDb)
    }

    val searchText = rememberSaveable(addWorkoutUiState.searchedTerm) {
        mutableStateOf(addWorkoutUiState.searchedTerm)
    }
    val date by rememberSaveable(workoutDate) {
        mutableStateOf(workoutDate)
    }
    var showAddWorkoutToLibraryDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    if (showAddWorkoutToLibraryDialog) {
        AddWorkoutToLibraryDialog(
            dismissDialog = {
                showAddWorkoutToLibraryDialog = false
            },
            addNewWorkoutToLibrary = { workoutName, workoutType ->
                addWorkoutUiState.handleAddWorkoutClickEvents(
                    AddWorkoutEvents.AddWorkoutToLibrary(
                        AddWorkoutToLibraryModel(
                            workoutName = workoutName,
                            workoutType = workoutType,
                            snackBarMessageId = R.string.text_workout_successfully_added_to_library
                        ),
                        context = context,
                        showSnackBar = {
                            showSnackBar(it)
                        }
                    )
                )
                showAddWorkoutToLibraryDialog = false
            }
        )
    }

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
        if (addWorkoutUiState.masterWorkoutList.isEmpty()) {
            AddWorkoutNoneScreen(
                modifier = Modifier.fillMaxSize(),
                openAddWorkoutToLibraryDialog = {
                    showAddWorkoutToLibraryDialog = true
                }
            )
        }
        if (addWorkoutUiState.masterWorkoutList.isNotEmpty()) {
            SearchBar(
                searchedTerm = searchText.value,
                updateSearch = { searchedText ->
                    addWorkoutUiState.handleAddWorkoutClickEvents(
                        AddWorkoutEvents.FilterSearchByWorkout(
                            searchedText
                        )
                    )
                },
                clearSearch = {
                    addWorkoutUiState.handleAddWorkoutClickEvents(AddWorkoutEvents.ClearSearchBarFilter)
                },
                keyboardController = keyboardController,
                focusManager = focusManager
            )
            UserWorkoutList(
                workoutList = addWorkoutUiState.listOfSearchedWorkouts,
                selectedWorkout = { name, type ->
                    navigateToDestination(
                        NavigationDirectionInterface.NavigateToAddWorkoutDetails(
                            name,
                            type,
                            date
                        )
                    )
                }
            )
        }
    }
}
