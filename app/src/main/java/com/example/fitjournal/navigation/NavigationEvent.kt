package com.example.fitjournal.navigation

import androidx.navigation.NavController

fun mainActivityNavigation(
    navigationInterface: NavigationInterface,
    navController: NavController
) {
    when (navigationInterface) {
        NavigationInterface.NavigateToHome -> {
            navController.navigate(Route.HOME_SCREEN)
        }
    }
}
