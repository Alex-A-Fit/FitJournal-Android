package com.example.fitjournal.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
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
    topAppBar: @Composable () -> Unit,
    mainScreen: @Composable (Modifier) -> Unit,
    navigateToDestination: (NavigationInterface) -> Unit,
    addWorkoutToDatabase: ((AddWorkoutToLibraryModel) -> Unit)? = null,
    updateChildFabDisplay: ((Boolean) -> Unit)? = null
) {
    var showWorkoutDialog by remember {
        mutableStateOf(false)
    }
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
            mainScreen(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
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
                        }
                    )
                    AddWorkoutFab(
                        showFloatingActionButtonValue = showChildrenFabIcons == true,
                        showFloatingActionButtons = {
                            updateChildFabDisplay?.invoke(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Test() {
    MaterialTheme.typography.bodySmall
}