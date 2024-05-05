package com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.theme.SuccessGreen
import com.example.fitjournal.core.presentation.theme.White

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonColors(
            containerColor = SuccessGreen,
            contentColor = White,
            disabledContainerColor = SuccessGreen,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary
        ),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.button_save),
            style = MaterialTheme.typography.titleLarge,
            modifier = textModifier
        )
    }
}
