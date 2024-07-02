package com.alexafit.fitjournal.library.presentation.screen.library

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
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
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.AddWorkoutToLibraryDialog
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.DeleteWorkoutDialog
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.TransparentLoadingScreenDialog
import com.alexafit.fitjournal.core.presentation.commoncomponents.textField.SearchBar
import com.alexafit.fitjournal.core.presentation.navigation.NavigationDirectionInterface
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.core.util.localdate.formatToCommonDate
import com.alexafit.fitjournal.library.domain.model.AddWorkoutToLibraryModel
import com.alexafit.fitjournal.library.presentation.components.AddNewWorkoutText
import com.alexafit.fitjournal.library.presentation.components.EditWorkoutAlertDialog
import com.alexafit.fitjournal.library.presentation.components.LibraryListSection
import com.alexafit.fitjournal.library.presentation.components.uistate.LibraryNoneScreen
import com.alexafit.fitjournal.library.presentation.model.LibraryWorkoutClickEvents
import com.alexafit.fitjournal.library.presentation.model.LibraryWorkoutUiModel
import java.time.LocalDate

@Composable
fun LibraryScreen(
    modifier: Modifier,
    libraryWorkoutState: LibraryWorkoutUiModel,
    isBlurActive: Boolean,
    removeBlur: (Boolean) -> Unit,
    libraryScreenListState: LazyListState,
    showSnackbar: suspend (String) -> Unit,
    navigateToDestination: (NavigationDirectionInterface) -> Unit
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
    var updateUi: Boolean by rememberSaveable { mutableStateOf(false) }
    var snackbarMessage: String by rememberSaveable { mutableStateOf("") }
    var showAddWorkoutToLibraryDialog by rememberSaveable { mutableStateOf(false) }

    val libraryWorkoutList by remember(libraryWorkoutState.masterWorkoutList) {
        mutableStateOf(libraryWorkoutState.masterWorkoutList)
    }

    if (showAddWorkoutToLibraryDialog) {
        AddWorkoutToLibraryDialog(
            dismissDialog = {
                showAddWorkoutToLibraryDialog = false
            },
            addNewWorkoutToLibrary = { workoutName, workoutType ->
                libraryWorkoutState.libraryWorkoutClickEvent(
                    LibraryWorkoutClickEvents.AddWorkoutToLibrary(
                        AddWorkoutToLibraryModel(
                            workoutName = workoutName,
                            workoutType = workoutType,
                            snackBarMessageId = R.string.text_workout_successfully_added_to_library
                        ),
                        context = context,
                        showSnackBar = {
                            showSnackbar(it)
                        }
                    )
                )
                showAddWorkoutToLibraryDialog = false
            }
        )
    }
    if (openEditLibraryWorkoutDialog) {
        EditWorkoutAlertDialog(
            onDismissRequest = { openEditLibraryWorkoutDialog = false },
            workoutItemDialogUiModel = libraryWorkoutState.workoutItemDialogUiModel,
            onUpdateButtonClick = { workoutName, type ->
                openEditLibraryWorkoutDialog = false
                showLoadingDialog = true
                libraryWorkoutState.libraryWorkoutClickEvent(
                    LibraryWorkoutClickEvents.UpdateLibraryWorkout(
                        workoutName = workoutName,
                        workoutTypeEnum = type,
                        context = context,
                        onSuccessCallback = {
                            updateUi = true
                            snackbarMessage = it
                        },
                        onErrorCallback = {
                            updateUi = false
                            showLoadingDialog = false
                            showSnackbar(it)
                        }
                    )
                )
            },
            onAddToJournalButtonClick = {
                openEditLibraryWorkoutDialog = false
                navigateToDestination(
                    NavigationDirectionInterface.NavigateToAddWorkoutDetails(
                        workoutName = libraryWorkoutState.workoutItemDialogUiModel.libraryWorkoutItem.workoutName,
                        workoutType = context.getString(libraryWorkoutState.workoutItemDialogUiModel.libraryWorkoutItem.workoutTypeEnum.stringId),
                        workoutDate = LocalDate.now().formatToCommonDate()
                    )
                )
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

    Box(modifier = Modifier.fillMaxSize()) {
        if (libraryWorkoutList.isEmpty()) {
            LibraryNoneScreen(
                modifier = Modifier.fillMaxSize(),
                openAddWorkoutToLibraryDialog = {
                    showAddWorkoutToLibraryDialog = true
                }
            )
        }
        if (libraryWorkoutList.isNotEmpty()) {
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
                AddNewWorkoutText(
                    modifier = Modifier.padding(vertical = Spacing.spacing8),
                    onClick = {
                        showAddWorkoutToLibraryDialog = true
                    }
                )
                LibraryListSection(
                    libraryWorkoutState = libraryWorkoutState,
                    isBlurActive = isBlurActive,
                    libraryScreenListState = libraryScreenListState,
                    showEditLibraryWorkoutDialog = { openEditLibraryWorkoutDialog = true },
                    removeBlur = removeBlur,
                    updateLibraryListUi = updateUi,
                    updateLibraryListUiCallback = {
                        updateUi = false
                        showLoadingDialog = false
                        showSnackbar(snackbarMessage)
                    }
                )
            }
        }
    }
}
