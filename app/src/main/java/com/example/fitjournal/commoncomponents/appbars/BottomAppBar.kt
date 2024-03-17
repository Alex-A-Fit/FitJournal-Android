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
import androidx.compose.ui.unit.dp
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
                painter = painterResource(id = R.drawable.icon_book),
                contentDescription = "Library for choosing workouts",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navigate(NavigationInterface.NavigateToWorkoutLibrary)
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_house),
                contentDescription = "Home tab to view workouts for the day",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navigate(NavigationInterface.NavigateToHome)
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_bar_chart),
                contentDescription = "Workout statistics",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        navigate(NavigationInterface.NavigateToWorkoutStatistics)
                    }
            )
        }
    }
}
