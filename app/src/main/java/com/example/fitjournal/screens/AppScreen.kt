package com.example.fitjournal.screens

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
import androidx.navigation.NavController
import com.example.fitjournal.R
import com.example.fitjournal.commoncomponents.appbars.BottomAppBar
import com.example.fitjournal.commoncomponents.dialogs.AddWorkoutToLibraryDialog
import com.example.fitjournal.commoncomponents.floatingactionbutton.AddWorkoutFab
import com.example.fitjournal.commoncomponents.floatingactionbutton.AnimatedFabColumn
import com.example.fitjournal.library.domain.model.AddWorkoutToLibraryModel
import com.example.fitjournal.navigation.NavigationInterface
import com.example.fitjournal.theme.Spacing

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
    addWorkoutToDatabase: ((AddWorkoutToLibraryModel) -> Unit)? = null,
    updateChildFabDisplay: ((Boolean) -> Unit)? = null,
    displayBlur: ((Boolean) -> Unit)? = null
) {
    var showWorkoutDialog by remember {
        mutableStateOf(false)
    }
    val interactionSource = remember { MutableInteractionSource() }

    if (showWorkoutDialog) {
        AddWorkoutToLibraryDialog(
            dismissDialog = {
                showWorkoutDialog = false
            },
            addNewWorkoutToLibrary = { workoutName, workoutType ->
                addWorkoutToDatabase?.invoke(
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
            BottomAppBar(
                navController = navController,
                navigate = { navRoute ->
                    navigateToDestination(navRoute)
                }
            )
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
                    .blur(if (showChildrenFabIcons == true) Spacing.blurDensity10 else Spacing.blurDensity0)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        displayBlur?.invoke(false)
                        updateChildFabDisplay?.invoke(false)
                    }
            ) {
                mainScreen(
                    Modifier
                        .padding(padding)
                        .fillMaxSize()
                )
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
                            updateChildFabDisplay?.invoke(false)
                            showWorkoutDialog = true
                            displayBlur?.invoke(false)
                        }
                    )
                    AddWorkoutFab(
                        showFloatingActionButtonValue = showChildrenFabIcons == true,
                        showFloatingActionButtons = {
                            updateChildFabDisplay?.invoke(it)
                            displayBlur?.invoke(it)
                        }
                    )
                }
            }
        }
    }
}
