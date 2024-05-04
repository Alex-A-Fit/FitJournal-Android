package com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fitjournal.core.presentation.theme.MediumGray
import com.example.fitjournal.core.presentation.theme.Red
import com.example.fitjournal.core.presentation.theme.White

@Composable
fun DeleteButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonColors(
            containerColor = Red,
            contentColor = White,
            disabledContainerColor = Color.Red,
            disabledContentColor = MediumGray
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = textModifier
        )
    }
}
