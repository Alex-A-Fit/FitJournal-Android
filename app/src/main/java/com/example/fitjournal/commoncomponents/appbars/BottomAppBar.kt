package com.example.fitjournal.commoncomponents.appbars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.navigation.NavigationInterface
import com.example.fitjournal.theme.Spacing

@Composable
fun BottomAppBar(
    navigate: (NavigationInterface) -> Unit
) {
    androidx.compose.material3.BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        contentPadding = PaddingValues(Spacing.spacing16)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_search_primary_alt),
                contentDescription = stringResource(id = R.string.content_desc_bottom_app_bar_library_icon),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(Spacing.spacing48)
                    .clickable {
                        navigate(NavigationInterface.NavigateToWorkoutLibrary)
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_journal_primary_alt),
                contentDescription = stringResource(id = R.string.content_desc_bottom_app_bar_home_icon),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(Spacing.spacing48)
                    .clickable {
                        navigate(NavigationInterface.NavigateToHome)
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_bar_chart),
                contentDescription = stringResource(id = R.string.content_desc_bottom_app_bar_stats_icon),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(Spacing.spacing48)
                    .clickable {
                        navigate(NavigationInterface.NavigateToWorkoutStatistics)
                    }
            )
        }
    }
}
