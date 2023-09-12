package com.example.fitjournal.navigation

sealed interface NavigationInterface {
    data object NavigateToHome : NavigationInterface
}
