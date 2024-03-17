package com.example.fitjournal.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fitjournal.commoncomponents.appbars.BottomAppBar
import com.example.fitjournal.commoncomponents.floatingactionbutton.AddCardFab
import com.example.fitjournal.navigation.NavigationInterface
import com.example.fitjournal.theme.Spacing

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    snackBarModifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit,
    snackBarHostState: SnackbarHostState,
    mainScreen: @Composable (Modifier) -> Unit,
    navigateToDestination: (NavigationInterface) -> Unit
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
            AddCardFab(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(padding)
                    .padding(
                        bottom = Spacing.spacing16,
                        end = Spacing.spacing16
                    ),
                navigateToAddWorkoutScreen = {}
            )
        }
    }
}
