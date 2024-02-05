package com.example.fitjournal.components.datepicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R
import com.example.fitjournal.ui.theme.Spacing

@Composable
fun DatePicker(
    modifier: Modifier,
    getPreviousDate: () -> Unit,
    getNextDate: () -> Unit,
    currentDate: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { getPreviousDate() }
        ){
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_back),
                contentDescription = "Arrow to navigate backwards through the calendar",
                modifier = Modifier.size(Spacing.spacing32)
            )
        }

        Text(
            text = currentDate,
            modifier = Modifier
                .padding(horizontal = Spacing.spacing16)
                .weight(1F, fill = false),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )

        IconButton(
            onClick = { getNextDate() }
        ){
            Icon(
                painter = painterResource(id = R.drawable.icon_arrow_forward),
                contentDescription = "Arrow to navigate forward through the calendar",
                modifier = Modifier.size(Spacing.spacing32)
            )
        }
    }
}