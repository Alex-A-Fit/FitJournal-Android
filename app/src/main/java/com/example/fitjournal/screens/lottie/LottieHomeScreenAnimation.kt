package com.example.fitjournal.screens.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.fitjournal.MainActivityUiState
import com.example.fitjournal.R
import com.example.fitjournal.navigation.NavigationInterface
import com.example.fitjournal.navigation.mainActivityNavigation

@Composable
fun LottieHomeScreenAnimation(
    mainActivityState: MainActivityUiState,
    navController: NavController
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fit_journal_animation))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress }
    )
    when (mainActivityState) {
        MainActivityUiState.Success -> mainActivityNavigation(
            navigationInterface = NavigationInterface.NavigateToHome,
            navController = navController
        )
        else -> Unit
    }
}
