package com.example.fitjournal.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.components.appbars.BottomAppBar
import com.example.fitjournal.navigation.NavigationInterface

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
        mainScreen(Modifier.padding(padding))
    }
}
