package com.example.fitjournal.core.presentation.commoncomponents.appbars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.navigation.Arguments
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.navigation.Route
import com.example.fitjournal.core.presentation.theme.Spacing

@Composable
fun BottomAppBar(
    navController: NavController,
    navigate: (NavigationInterface) -> Unit
) {
    androidx.compose.material3.BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        contentPadding = PaddingValues(Spacing.spacing16)
    ) {
        NavigationBar(
            modifier = Modifier.background(Color.Transparent),
            containerColor = Color.Transparent,
            contentColor = Color.Transparent
        ) {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry.value?.destination

            NavigationBarItem(
                selected = false,
                onClick = { navigate(NavigationInterface.NavigateToWorkoutLibrary) },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_search_primary_alt),
                        contentDescription = stringResource(id = R.string.content_desc_bottom_app_bar_library_icon),
                        tint = if (currentDestination?.route == Route.WORKOUT_LIBRARY_SCREEN) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.text_library_tab_bar_icon),
                        color = if (currentDestination?.route == Route.WORKOUT_LIBRARY_SCREEN) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
                    )
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = { navigate(NavigationInterface.NavigateToHome) },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_journal_primary_alt),
                        contentDescription = stringResource(id = R.string.content_desc_bottom_app_bar_home_icon),
                        tint = if (currentDestination?.route == Route.HOME_SCREEN) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.text_journal_tab_bar_icon),
                        color = if (currentDestination?.route == Route.HOME_SCREEN) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
                    )
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = { navigate(NavigationInterface.NavigateToWorkoutStatistics) },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_bar_chart),
                        contentDescription = stringResource(id = R.string.content_desc_bottom_app_bar_stats_icon),
                        tint = if (currentDestination?.route == Route.WORKOUT_STATISTICS_SCREEN || currentDestination?.route == "${Route.WORKOUT_STATISTICS_DETAILS_SCREEN}${Arguments.WORKOUT_NAME}") MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.text_statistics_tab_bar_icon),
                        color = if (currentDestination?.route == Route.WORKOUT_STATISTICS_SCREEN || currentDestination?.route == "${Route.WORKOUT_STATISTICS_DETAILS_SCREEN}${Arguments.WORKOUT_NAME}") MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    }
}
