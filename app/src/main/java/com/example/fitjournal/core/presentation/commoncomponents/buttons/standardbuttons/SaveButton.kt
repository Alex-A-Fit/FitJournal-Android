package com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.theme.SuccessGreen
import com.example.fitjournal.core.presentation.theme.White

@Composable
fun SaveButton(
    text: String,
    modifier: Modifier = Modifier,
    buttonColor: ButtonColors = ButtonColors(
        containerColor = SuccessGreen,
        contentColor = White,
        disabledContainerColor = SuccessGreen,
        disabledContentColor = MaterialTheme.colorScheme.onTertiary
    ),
    textModifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = buttonColor,
        modifier = modifier,
        enabled = isEnabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = textModifier
        )
    }
}
