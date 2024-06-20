package com.alexafit.fitjournal.library.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.appbars.TopAppBar

@Composable
fun LibraryTopAppBar() {
    TopAppBar(
        appBarTitle = {
            Text(
                text = stringResource(id = R.string.title_workout_library),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}
