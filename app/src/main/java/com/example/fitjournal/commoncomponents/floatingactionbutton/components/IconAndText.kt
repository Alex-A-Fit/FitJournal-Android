package com.example.fitjournal.commoncomponents.floatingactionbutton.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.theme.Spacing

@Composable
fun IconAndText(
    iconId: Int,
    iconContentDescription: String,
    iconModifier: Modifier = Modifier,
    iconText: String,
    textModifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = Spacing.spacing8)
    ) {
        Image(
            painter = painterResource(
                id = iconId
            ),
            contentDescription = iconContentDescription,
            modifier = iconModifier,
            contentScale = ContentScale.FillHeight
        )

        Spacer(modifier = Modifier.width(Spacing.spacing8))

        Text(
            text = iconText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = textModifier,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
    }
}
