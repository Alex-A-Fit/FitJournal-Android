package com.example.fitjournal.home.presentation.components.appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.appbars.TopAppBar
import com.example.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons.NavigateUpIconButton

@Composable
fun EditWorkoutTopAppBar(
    navigateUp: () -> Unit
) {
    TopAppBar(
        appBarTitle = {
            Text(
                text = stringResource(id = R.string.title_edit_workout),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            NavigateUpIconButton(navigateUp = navigateUp)
        },
        modifier = Modifier.fillMaxWidth()
    )
}
