package com.example.fitjournal.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.components.BottomAppBar
import com.example.fitjournal.components.TopAppBar
import com.example.fitjournal.navigation.NavigationInterface
import com.example.fitjournal.screens.home.HomeScreen
import com.example.fitjournal.screens.home.HomeScreenTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    modifier: Modifier,
    screenText: String,
    navigateToDestination: (NavigationInterface) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(appBarTitle = { HomeScreenTitle() })
        },
        bottomBar = {
            BottomAppBar(
                navigate = { navRoute ->
                    navigateToDestination(navRoute)
                }
            )
        }
    ) { padding ->
        HomeScreen(
            text = screenText,
            modifier = Modifier.padding(padding)
        )
    }
}