package com.example.fitjournal.core.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.appbars.BottomAppBar
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.AddWorkoutToLibraryDialog
import com.example.fitjournal.core.presentation.commoncomponents.floatingactionbutton.AddWorkoutFab
import com.example.fitjournal.core.presentation.commoncomponents.floatingactionbutton.AnimatedFabColumn
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.domain.model.AddWorkoutToLibraryModel
import com.skydoves.cloudy.Cloudy

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    showChildrenFabIcons: Boolean? = null,
    showMainFabIcon: Boolean = true,
    snackBarModifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    navController: NavController,
    topAppBar: @Composable () -> Unit,
    mainScreen: @Composable (Modifier) -> Unit,
    navigateToDestination: (NavigationInterface) -> Unit,
    addWorkoutToLibraryItemDatabase: ((AddWorkoutToLibraryModel) -> Unit)? = null,
    displayChildFabs: ((Boolean) -> Unit)? = null,
    bottomBarVisibility: Boolean = true
) {
    var showWorkoutDialog by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    if (showWorkoutDialog) {
        AddWorkoutToLibraryDialog(
            dismissDialog = {
                showWorkoutDialog = false
            },
            addNewWorkoutToLibrary = { workoutName, workoutType ->
                addWorkoutToLibraryItemDatabase?.invoke(
                    AddWorkoutToLibraryModel(
                        workoutName = workoutName,
                        workoutType = workoutType,
                        snackBarMessageId = R.string.text_workout_successfully_added_to_library
                    )
                )
                showWorkoutDialog = false
            }

        )
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            topAppBar()
        },
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisibility,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomAppBar(
                    navController = navController,
                    navigate = { navRoute ->
                        navigateToDestination(navRoute)
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = snackBarModifier.clip(RoundedCornerShape(Spacing.spacing16))
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        displayChildFabs?.invoke(false)
                    }
                    .then(
                        if (android.os.Build.VERSION.SDK_INT > 30) {
                            Modifier
                                .blur(if (showChildrenFabIcons == true) Spacing.blurDensity10 else Spacing.blurDensity0)
                        } else {
                            Modifier
                        }
                    )
            ) {
                if (android.os.Build.VERSION.SDK_INT < 30) {
                    if (showChildrenFabIcons == true) {
                        Cloudy(
                            radius = 25,
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                displayChildFabs?.invoke(false)
                                focusManager.clearFocus(force = true)
                            }
                        ) {
                            mainScreen(
                                Modifier
                                    .padding(padding)
                                    .fillMaxSize()
                            )
                        }
                    } else {
                        mainScreen(
                            Modifier
                                .padding(padding)
                                .fillMaxSize()
                        )
                    }
                } else {
                    mainScreen(
                        Modifier
                            .padding(padding)
                            .fillMaxSize()
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                displayChildFabs?.invoke(false)
                            }
                    )
                }
            }
            if (showMainFabIcon) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding)
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    AnimatedFabColumn(
                        showFabs = showChildrenFabIcons == true,
                        navigateToAddToLibraryScreen = {
                            displayChildFabs?.invoke(false)
                            showWorkoutDialog = true
                        },
                        navigateToJournalEntry = {
                            displayChildFabs?.invoke(false)
                            navigateToDestination(NavigationInterface.NavigateToJournalEntry)
                        }
                    )
                    AddWorkoutFab(
                        showFloatingActionButtonValue = showChildrenFabIcons == true,
                        showFloatingActionButtons = {
                            displayChildFabs?.invoke(it)
                        }
                    )
                }
            }
        }
    }
}
