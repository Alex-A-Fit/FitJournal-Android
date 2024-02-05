package com.example.fitjournal.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.components.navigation.BottomAppBar
import com.example.fitjournal.components.navigation.TopAppBar
import com.example.fitjournal.navigation.NavigationInterface

@Composable
fun AppScreen(
    modifier: Modifier,
    appBarTitle: @Composable () -> Unit,
    appBarEndAlignedActionIcon: (@Composable () -> Unit)? = null,
    mainScreen: @Composable (Modifier) -> Unit,
    navigateToDestination: (NavigationInterface) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                appBarTitle = { appBarTitle() },
                modifier = Modifier.fillMaxWidth(),
                endAlignedActionIcon = appBarEndAlignedActionIcon
            )
        },
        bottomBar = {
            BottomAppBar(
                navigate = { navRoute ->
                    navigateToDestination(navRoute)
                }
            )
        }
    ) { padding ->
        mainScreen(Modifier.padding(padding))
    }
}