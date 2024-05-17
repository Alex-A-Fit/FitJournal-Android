package com.example.fitjournal.core.presentation.commoncomponents.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.core.presentation.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentLoadingScreenDialog(
    onBackPress: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = {
            onBackPress()
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(Spacing.spacing32)
            )
            .border(
                width = Spacing.spacing2,
                color = Color.Transparent,
                shape = RoundedCornerShape(Spacing.spacing32)
            ),
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        ),
        content = {
            Box(
                modifier = Modifier.background(color = Color.Transparent),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Spacing.spacing75),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}
