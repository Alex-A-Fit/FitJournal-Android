package com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun CancelButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary
        ),
        border = BorderStroke(
            width = Spacing.spacing2,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.button_cancel),
            style = MaterialTheme.typography.titleLarge,
            modifier = textModifier
        )
    }
}
