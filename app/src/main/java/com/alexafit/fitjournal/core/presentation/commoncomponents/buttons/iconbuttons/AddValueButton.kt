package com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.Spacing

@Composable
fun AddValueButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(Spacing.spacing8),
        contentPadding = PaddingValues(Spacing.spacing4)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.content_desc_add_value_icon),
            modifier = Modifier.size(Spacing.spacing32),
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}
