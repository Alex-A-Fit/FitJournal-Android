package com.example.fitjournal.commoncomponents.dialogs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicDialog(
    properties: DialogProperties = DialogProperties(),
    dismissEvent: () -> Unit,
    dialogContent: @Composable () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = {
            dismissEvent()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Spacing.spacing12,
                vertical = Spacing.spacing24
            )
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(Spacing.spacing32)
            )
            .border(
                width = Spacing.spacing2,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(Spacing.spacing32)
            ),
        properties = properties,
        content = {
            dialogContent()
        }
    )
}
