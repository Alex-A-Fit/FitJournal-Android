package com.alexafit.fitjournal.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alexafit.fitjournal.core.presentation.navigation.Route.LOTTIE_INTRO
import com.alexafit.fitjournal.home.presentation.model.state.HomeScreenUiState
import com.alexafit.fitjournal.library.domain.model.AddWorkoutToLibraryModel

@Composable
fun FitJournalNavHost(
    isThisUserFirstTime: Boolean,
    showSnackBar: (String) -> Unit,
    persistentHomeScreenData: PersistentHomeScreenData,
    snackBarState: SnackbarHostState,
    addWorkoutToLibraryItemDatabase: (
        AddWorkoutToLibraryModel,
        getDataFromRealmDb: () -> Unit
    ) -> Unit
) {
    val navController = rememberNavController()
    val showChildFabs = remember { mutableStateOf(false) }
    val bottomBarVisibility = remember { (mutableStateOf(true)) }
    val showAds = remember { (mutableStateOf(true)) }
    val homeScreenListState = rememberLazyListState()
    val libraryScreenListState = rememberLazyListState()

    val navigationManager = NavigationManager(
        navController = navController,
        snackBarState = snackBarState,
        bottomBarVisibility = bottomBarVisibility,
        showAds = showAds,
        setChildFabsVisibility = {
            showChildFabs.value = it
        },
        showChildFabs = showChildFabs,
        homeScreenListState = homeScreenListState,
        libraryScreenListState = libraryScreenListState,
        showSnackBar = showSnackBar,
        addWorkoutToLibraryItemDatabase = addWorkoutToLibraryItemDatabase,
        isThisUserFirstTime = isThisUserFirstTime,
        persistentHomeScreenData = persistentHomeScreenData
    )

    NavHost(
        navController = navController,
        modifier = Modifier.fillMaxSize(),
        startDestination = LOTTIE_INTRO
    ) {
        with(navigationManager) {
            onboardingScreen()
            libraryScreen()
            homeScreen()
            statisticsScreen()
            statisticsDetailsScreen()
            addWorkoutScreen()
            addWorkoutDetailsScreen()
            lottieIntroScreen()
            editJournalScreen()
        }
    }
}

data class PersistentHomeScreenData(
    val uiState: HomeScreenUiState,
    val getWorkoutsFromDatabase: () -> Unit
)
