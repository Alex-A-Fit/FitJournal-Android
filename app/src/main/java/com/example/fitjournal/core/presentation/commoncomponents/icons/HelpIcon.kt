package com.example.fitjournal.core.presentation.commoncomponents.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.theme.DarkGray2
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun HelpIcon(
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .padding(end = Spacing.spacing8)
            .background(
                Color.Transparent,
                shape = RoundedCornerShape(percent = 100)
            )
            .border(
                width = Spacing.spacing2,
                color = DarkGray2,
                shape = RoundedCornerShape(percent = 100)
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_question_mark),
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}
