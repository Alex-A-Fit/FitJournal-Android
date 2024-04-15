package com.example.fitjournal.core.presentation.commoncomponents.floatingactionbutton

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.TransformOrigin
import com.example.fitjournal.core.presentation.commoncomponents.floatingactionbutton.components.AddToJournalFab
import com.example.fitjournal.core.presentation.commoncomponents.floatingactionbutton.components.AddToLibraryFab

@Composable
fun AnimatedFabColumn(
    showFabs: Boolean,
    navigateToAddToLibraryScreen: () -> Unit,
    navigateToJournalEntry: () -> Unit
) {
    // These pivots set a point of reference to
    // where the animation originates from and exits to
    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = showFabs,
            enter = slideInVertically() + scaleIn(
                transformOrigin = TransformOrigin(
                    pivotFractionX = 0.8f,
                    pivotFractionY = 0.5f
                )
            ),
            exit = slideOutVertically() + scaleOut(
                transformOrigin = TransformOrigin(
                    pivotFractionX = 0.8f,
                    pivotFractionY = 0.5f
                )
            )
        ) {
            AddToJournalFab(navigateToAddWorkoutToJournalScreen = {
                navigateToJournalEntry()
            })
            AddToLibraryFab(navigateToAddWorkoutToLibraryScreen = {
                navigateToAddToLibraryScreen()
            })
        }
    }
}
