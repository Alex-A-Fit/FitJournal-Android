package com.example.fitjournal.statistics.presentation.components.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R

@Composable
fun GraphTitle(
    text: String,
    showBackArrow: Boolean,
    onBackArrowClicked: () -> Unit = {},
    onNextArrowClicked: () -> Unit = {},
    showNextArrow: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_arrow_back),
            contentDescription = stringResource(id = R.string.content_desc_graph_title_back_arrow),
            tint = if (showBackArrow) MaterialTheme.colorScheme.onPrimary else Color.Transparent,
            modifier = if (showBackArrow) {
                Modifier.clickable {
                    onBackArrowClicked()
                }
            } else {
                Modifier
            }
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.icon_arrow_forward),
            contentDescription = stringResource(id = R.string.content_desc_graph_title_forward_arrow),
            tint = if (showNextArrow) MaterialTheme.colorScheme.onPrimary else Color.Transparent,
            modifier = if (showNextArrow) {
                Modifier.clickable {
                    onNextArrowClicked()
                }
            } else {
                Modifier
            }
        )
    }
}
