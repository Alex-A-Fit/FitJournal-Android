package com.example.fitjournal.library.presentation.screen.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
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
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.DeleteWorkoutDialog
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.TransparentLoadingScreenDialog
import com.example.fitjournal.core.presentation.commoncomponents.textField.SearchBar
import com.example.fitjournal.core.presentation.model.LibraryWorkoutItem
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.home.presentation.model.events.HomeScreenEvents
import com.example.fitjournal.library.presentation.screen.library.components.EditWorkoutAlertDialog
import com.example.fitjournal.library.presentation.screen.library.components.LibraryListSection
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel

@Composable
fun LibraryScreen(
    modifier: Modifier,
    libraryWorkoutState: LibraryWorkoutUiModel,
    isBlurActive: Boolean,
    removeBlur: (Boolean) -> Unit,
    libraryScreenListState: LazyListState,
    showSnackbar: suspend (String) -> Unit,
    navigateToDestination: (NavigationInterface) -> Unit
) {
    LaunchedEffect(key1 = true) {
        libraryWorkoutState.libraryWorkoutClickEvent(LibraryWorkoutClickEvents.SyncRealmWorkoutEntryFromDb)
    }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    var openEditLibraryWorkoutDialog by rememberSaveable { mutableStateOf(false) }
    var openDeleteWorkoutDialog by rememberSaveable { mutableStateOf(false) }
    var showLoadingDialog by rememberSaveable { mutableStateOf(false) }
    var updateUi by rememberSaveable { mutableStateOf(false) }


    if (openEditLibraryWorkoutDialog) {
        EditWorkoutAlertDialog(
            onDismissRequest = { openEditLibraryWorkoutDialog = false },
            workoutItemDialogUiModel = libraryWorkoutState.workoutItemDialogUiModel,
            onUpdateButtonClick = {

            },
            onAddToJournalButtonClick = {

            },
            onDeleteButtonClick = {
                openEditLibraryWorkoutDialog = false
                openDeleteWorkoutDialog = true
            }
        )
    }
    if (openDeleteWorkoutDialog) {
        DeleteWorkoutDialog(
            workoutName = libraryWorkoutState.workoutItemDialogUiModel.libraryWorkoutItem.workoutName,
            workoutDate = null,
            onDismiss = {
                openDeleteWorkoutDialog = false
            },
            onDelete = {
                openDeleteWorkoutDialog = false
                showLoadingDialog = true
                libraryWorkoutState.libraryWorkoutClickEvent(
                    LibraryWorkoutClickEvents.DeleteLibraryWorkout(
                        onSuccessCallback = {
                            updateUi = true
                            showLoadingDialog = false
                            showSnackbar(it)
                        },
                        onErrorCallback = {
                            updateUi = false
                            showLoadingDialog = false
                            showSnackbar(it)
                        },
                        context = context
                    )
                )
            }
        )
    }

    if (showLoadingDialog) {
        TransparentLoadingScreenDialog {}
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
                removeBlur(true)
            }
    ) {
        SearchBar(
            searchedTerm = libraryWorkoutState.searchedTerm,
            updateSearch = { searchedText ->
                libraryWorkoutState.libraryWorkoutClickEvent(
                    LibraryWorkoutClickEvents.UpdateSearch(searchedText)
                )
            },
            clearSearch = {
                libraryWorkoutState.libraryWorkoutClickEvent(
                    LibraryWorkoutClickEvents.ClearSearch
                )
            },
            keyboardController = keyboardController,
            focusManager = focusManager
        )
        LibraryListSection(
            libraryWorkoutState = libraryWorkoutState,
            isBlurActive = isBlurActive,
            libraryScreenListState = libraryScreenListState,
            showEditLibraryWorkoutDialog = { openEditLibraryWorkoutDialog = true },
            removeBlur = removeBlur,
            updateUi = updateUi
        )
    }
}
