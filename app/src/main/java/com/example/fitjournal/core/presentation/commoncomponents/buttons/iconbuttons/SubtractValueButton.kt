package com.example.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun SubtractValueButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(Spacing.spacing8),
        contentPadding = PaddingValues(Spacing.spacing4)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_horizontal_line),
            contentDescription = stringResource(id = R.string.content_desc_subtract_value_icon),
            modifier = Modifier.size(Spacing.spacing32),
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}
