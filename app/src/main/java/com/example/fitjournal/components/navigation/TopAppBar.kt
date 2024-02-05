package com.example.fitjournal.components.navigation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    appBarTitle: @Composable () -> Unit,
    endAlignedActionIcon: (@Composable () -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    modifier: Modifier
) {
    CenterAlignedTopAppBar(
        title = appBarTitle,
        modifier = modifier,
        actions = { if (endAlignedActionIcon != null) endAlignedActionIcon() },
        navigationIcon = navigationIcon ?: {},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}