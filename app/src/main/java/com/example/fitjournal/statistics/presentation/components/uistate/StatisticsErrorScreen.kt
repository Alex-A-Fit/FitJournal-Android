package com.example.fitjournal.statistics.presentation.components.uistate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.theme.DarkGray
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.presentation.theme.White
import com.example.fitjournal.core.util.constants.Zero
@Composable
fun StatisticsErrorScreen() {
    val gradient = Brush.linearGradient(
        Zero.FLOAT to MaterialTheme.colorScheme.primary,
        1.0f to White,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing16)
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_error),
                modifier = Modifier.size(Spacing.spacing128),
                contentDescription = stringResource(id = R.string.content_desc_error_icon)
            )
            Text(
                text = stringResource(id = R.string.error_statistics_title),
                style = MaterialTheme.typography.headlineMedium,
                color = DarkGray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Spacing.spacing16))
        }
    }
}
