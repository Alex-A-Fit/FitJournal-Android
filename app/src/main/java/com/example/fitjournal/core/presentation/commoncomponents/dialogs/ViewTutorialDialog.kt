package com.example.fitjournal.core.presentation.commoncomponents.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.CancelButton
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.BasicDialog
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun ViewTutorialDialog(
    properties: DialogProperties = DialogProperties(),
    onConfirmDialog: () -> Unit,
    onDismissDialog: () -> Unit
) {
    BasicDialog(
        dismissEvent = onDismissDialog,
        properties = properties
    ) {
        ViewTutorialSection(
            onConfirmDialog = onConfirmDialog,
            onDismissDialog = onDismissDialog,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing16)
        )
    }
}

@Composable
fun ViewTutorialSection(
    modifier: Modifier = Modifier,
    onConfirmDialog: () -> Unit,
    onDismissDialog: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_question_mark),
            contentDescription = stringResource(
                id = R.string.content_desc_home_screen_help_icon
            ),
            modifier = Modifier.size(Spacing.spacing96),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(Spacing.spacing16))
        Text(
            text = stringResource(id = R.string.title_view_tutorial_again),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(Spacing.spacing16))

        SaveButton(text = stringResource(id = R.string.button_watch_tutorial)) {
            onConfirmDialog()
        }
        Spacer(modifier = Modifier.height(Spacing.spacing16))

        CancelButton {
            onDismissDialog()
        }
    }
}
