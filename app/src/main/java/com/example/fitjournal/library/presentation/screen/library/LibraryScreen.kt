package com.example.fitjournal.library.presentation.screen.library

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.DeleteWorkoutDialog
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.TransparentLoadingScreenDialog
import com.example.fitjournal.core.presentation.commoncomponents.textField.SearchBar
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.util.localdate.formatToCommonDate
import com.example.fitjournal.library.presentation.screen.library.components.EditWorkoutAlertDialog
import com.example.fitjournal.library.presentation.screen.library.components.LibraryListSection
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel
import com.example.fitjournal.library.presentation.screen.library.utils.createAnnotatedString
import java.time.LocalDate

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
    var updateUi: Boolean by rememberSaveable { mutableStateOf(false) }
    var snackbarMessage: String by rememberSaveable { mutableStateOf("") }
    val annotatedString = createAnnotatedString(text = stringResource(id = R.string.text_add_to_workout))

    Box(modifier = Modifier.fillMaxSize()) {
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
                        NavigationInterface.NavigateToAddWorkoutDetails(
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
            ClickableText(
                text = annotatedString,
                onClick = {
                    Log.d("LibraryScreen: ", "THE CLICKABLE TEXT WAS CLICKED")
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
