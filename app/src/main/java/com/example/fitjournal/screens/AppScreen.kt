package com.example.fitjournal.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fitjournal.commoncomponents.appbars.BottomAppBar
import com.example.fitjournal.commoncomponents.floatingactionbutton.AddWorkoutFab
import com.example.fitjournal.commoncomponents.floatingactionbutton.AnimatedFabColumn
import com.example.fitjournal.navigation.NavigationInterface

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
    updateChildFabDisplay: ((Boolean) -> Unit)? = null
) {
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
                modifier = snackBarModifier
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
                    AnimatedFabColumn(showChildrenFabIcons == true)
                    AddWorkoutFab(
                        showFloatingActionButtonValue = showChildrenFabIcons == true,
                        showFloatingActionButtons = { updateChildFabDisplay?.invoke(it) }
                    )
                }
            }
        }
    }
}
