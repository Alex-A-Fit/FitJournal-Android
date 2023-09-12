package com.example.fitjournal.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@ExperimentalMaterial3Api
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { HomeScreenTitle() },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) {paddingValues ->
        Text(text = "Stuff", modifier = Modifier.padding(paddingValues))
    }

}

@Composable
fun HomeScreenTitle(){
    Text(
        text = "Journal",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
}