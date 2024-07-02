package com.alexafit.fitjournal.core.presentation.screens.lottie

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.navigation.NavigationDirectionInterface
import com.alexafit.fitjournal.core.presentation.navigation.navigationEvent

@Composable
fun LottieHomeScreenAnimation(
    navController: NavController,
    isThisUserFirstTime: Boolean
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
    if (progress == 1f) {
        if (isThisUserFirstTime) {
            navigationEvent(
                navigationDirectionInterface = NavigationDirectionInterface.NavigateToOnboarding,
                navController = navController
            )
        } else {
            navigationEvent(
                navigationDirectionInterface = NavigationDirectionInterface.NavigateToHome,
                navController = navController
            )
        }
    }
}
