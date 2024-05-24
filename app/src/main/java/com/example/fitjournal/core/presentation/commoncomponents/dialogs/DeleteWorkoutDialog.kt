package com.example.fitjournal.core.presentation.commoncomponents.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.CancelButton
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.DeleteButton
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.BasicDialog
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun DeleteWorkoutDialog(
    workoutName: String,
    workoutDate: String?,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    BasicDialog(dismissEvent = { onDismiss() }) {
        DeleteWorkoutSection(
            workoutName = workoutName,
            workoutDate = workoutDate,
            onDismiss = onDismiss,
            onDelete = onDelete
        )
    }
}

@Composable
fun DeleteWorkoutSection(
    workoutName: String,
    workoutDate: String?,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing.spacing16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .height(Spacing.spacing96)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.icon_journal),
            contentDescription = stringResource(id = R.string.content_desc_journal_filled_icon)
        )
        Spacer(modifier = Modifier.height(Spacing.spacing16))
        Text(
            text = stringResource(
                id = R.string.title_delete_workout,
                workoutName
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        workoutDate?.let {
            Spacer(modifier = Modifier.height(Spacing.spacing8))
            Text(
                text = stringResource(
                    id = R.string.subtitle_delete_workout,
                    it
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(Spacing.spacing24))
        CancelButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onDismiss() }
        )
        Spacer(modifier = Modifier.height(Spacing.spacing16))
        DeleteButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.button_delete),
            onClick = { onDelete() }
        )
    }
}
