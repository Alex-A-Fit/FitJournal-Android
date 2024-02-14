package com.example.fitjournal.components.icons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.fitjournal.R

@Composable
fun FilterIcon(
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_icon_filter),
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}