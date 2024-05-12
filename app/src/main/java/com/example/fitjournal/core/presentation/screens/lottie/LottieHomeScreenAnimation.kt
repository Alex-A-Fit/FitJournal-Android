package com.example.fitjournal.core.presentation.screens.lottie

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.fitjournal.MainActivityUiState
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.navigation.navigationEvent

@Composable
fun LottieHomeScreenAnimation(
    mainActivityState: MainActivityUiState,
    navController: NavController
) {
    val nightMode = isSystemInDarkTheme()
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            if (nightMode) R.raw.fit_journal_animation_night else R.raw.fit_journal_animation
        )
    )
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress }
    )
    when (mainActivityState) {
        MainActivityUiState.Success -> navigationEvent(
            navigationInterface = NavigationInterface.NavigateToHome,
            navController = navController
        )
        else -> Unit
    }
}
