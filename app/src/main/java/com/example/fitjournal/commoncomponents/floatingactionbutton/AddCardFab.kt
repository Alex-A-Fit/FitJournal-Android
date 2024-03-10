package com.example.fitjournal.commoncomponents.floatingactionbutton

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.fitjournal.R
import com.example.fitjournal.theme.Spacing

@Composable
fun AddCardFab(
    modifier: Modifier = Modifier,
    navigateToAddWorkoutScreen: () -> Unit
) {
    FloatingActionButton(
        onClick = { navigateToAddWorkoutScreen() },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_add_circle),
            contentDescription = "A plus sign to add a card",
            modifier = Modifier
                .size(Spacing.spacing64)
                .padding(Spacing.spacing2)
        )
    }
}
