package com.example.fitjournal.library.presentation.screen.library

import LibraryListSection
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.library.presentation.screen.library.components.LibrarySearchBar
import com.example.fitjournal.theme.Spacing

@Composable
fun LibraryScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.spacing16)
    ) {
        LibrarySearchBar()
        LibraryListSection()
    }
}
