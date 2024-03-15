package com.example.fitjournal.commoncomponents.floatingactionbutton.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.fitjournal.theme.Percent
import com.example.fitjournal.theme.Spacing

@Composable
fun TextAndFab(
    onClickEvent: () -> Unit,
    modifier: Modifier = Modifier,
    textId: Int,
    iconId: Int,
    iconContentDescId: Int
) {
    Text(
        text = stringResource(id = textId),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier.padding(bottom = Spacing.spacing16)
    )
    Spacer(modifier = Modifier.width(Spacing.spacing8))
    SmallFloatingActionButton(
        onClick = { onClickEvent() },
        modifier = modifier
            .padding(
                bottom = Spacing.spacing16,
                end = Spacing.spacing16
            ),
        shape = RoundedCornerShape(percent = Percent.percent50),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = stringResource(id = iconContentDescId)
        )
    }
}
