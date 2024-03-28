package com.example.fitjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitjournal.commoncomponents.appbars.TopAppBar
import com.example.fitjournal.home.presentation.components.appbar.HomeTopAppBar
import com.example.fitjournal.home.presentation.model.events.HomeAppBarEvents
import com.example.fitjournal.home.presentation.model.events.HomeScreenEvents
import com.example.fitjournal.home.presentation.screen.home.HomeScreen
import com.example.fitjournal.home.presentation.screen.home.HomeScreenViewModel
import com.example.fitjournal.library.presentation.screen.library.LibraryScreen
import com.example.fitjournal.library.presentation.screen.library.LibraryScreenViewModel
import com.example.fitjournal.navigation.NavigationInterface
import com.example.fitjournal.navigation.Route
import com.example.fitjournal.navigation.Route.LOTTIE_INTRO
import com.example.fitjournal.navigation.navigationEvent
import com.example.fitjournal.screens.AppScreen
import com.example.fitjournal.screens.lottie.LottieHomeScreenAnimation
import com.example.fitjournal.theme.FitJournalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeScreenViewModel by viewModels()
    private val libraryScreenViewModel: LibraryScreenViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.runSplashScreen()
        setContent {
            FitJournalTheme {
                val navController = rememberNavController()
                val snackBarState = remember { SnackbarHostState() }
                var showChildFabs by remember {
                    mutableStateOf(false)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    NavHost(
                        navController = navController,
                        modifier = Modifier.fillMaxSize(),
                        startDestination = LOTTIE_INTRO
                    ) {
                        composable(Route.WORKOUT_LIBRARY_SCREEN) {
                            AppScreen(
                                showChildrenFabIcons = showChildFabs,
                                modifier = Modifier,
                                mainScreen = { mainScreenModifier ->
                                    LibraryScreen(
                                        modifier = mainScreenModifier,
                                        libraryWorkoutState = libraryScreenViewModel.libraryWorkoutState,
                                        displayBlur =  { showChildFabs = it }
                                    )
                                },
                                snackBarHostState = snackBarState,
                                topAppBar = {
                                    TopAppBar(
                                        appBarTitle = {
                                            Text(
                                                text = "Journal",
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                },
                                updateChildFabDisplay = {
                                    showChildFabs = it
                                },
                                addWorkoutToDatabase = { addWorkoutToDbModel ->
                                    mainViewModel.addWorkoutToDatabase(
                                        workoutName = addWorkoutToDbModel.workoutName,
                                        workoutTypeEnum = addWorkoutToDbModel.workoutType,
                                        successCallback = {
                                            showSnackBar(
                                                snackBarHostState = snackBarState,
                                                message = getString(
                                                    addWorkoutToDbModel.snackBarMessageId,
                                                    addWorkoutToDbModel.workoutName
                                                )
                                            )
                                        }
                                    )
                                },
                                navController = navController
                            )
                        }
                        composable(Route.HOME_SCREEN) {
                            AppScreen(
                                showChildrenFabIcons = showChildFabs,
                                modifier = Modifier,
                                snackBarHostState = snackBarState,
                                topAppBar = {
                                    HomeTopAppBar(
                                        currentDate = homeViewModel.homeScreenState.currentDate,
                                        homeAppBarEvents = ::homeAppBarEvents
                                    )
                                },
                                mainScreen = { mainScreenModifier ->
                                    HomeScreen(
                                        modifier = mainScreenModifier.fillMaxSize(),
                                        homeScreenState = homeViewModel.homeScreenState,
                                        homeScreenEvents = ::homeScreenEvents,
                                        snackBarHostState = snackBarState
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                },
                                updateChildFabDisplay = {
                                    showChildFabs = it
                                },
                                addWorkoutToDatabase = { addWorkoutToDbModel ->
                                    mainViewModel.addWorkoutToDatabase(
                                        workoutName = addWorkoutToDbModel.workoutName,
                                        workoutTypeEnum = addWorkoutToDbModel.workoutType,
                                        successCallback = {
                                            showSnackBar(
                                                snackBarHostState = snackBarState,
                                                message = getString(
                                                    addWorkoutToDbModel.snackBarMessageId,
                                                    addWorkoutToDbModel.workoutName
                                                )
                                            )
                                        }
                                    )
                                },
                                displayBlur = {
                                    showChildFabs = it
                                },
                                navController = navController
                            )
                        }
                        composable(Route.WORKOUT_STATISTICS_SCREEN) {
                            AppScreen(
                                showMainFabIcon = false,
                                modifier = Modifier,
                                snackBarHostState = snackBarState,
                                mainScreen = { mainScreenModifier ->
//                                    HomeScreen(
//                                        modifier = mainScreenModifier,
//                                        homeScreenState = homeViewModel.homeScreenState,
//                                        homeScreenEvents = ::homeScreenEvents,
//                                        snackBarHostState = snackState
//                                        )
                                },
                                topAppBar = {
                                    TopAppBar(
                                        appBarTitle = {
                                            Text(
                                                text = "Journal",
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                },
                                navController = navController
                            )
                        }
                        composable(LOTTIE_INTRO) {
                            LottieHomeScreenAnimation(
                                mainActivityState = mainViewModel.appScreenState,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }

    private fun homeScreenEvents(events: HomeScreenEvents) {
        when (events) {
            HomeScreenEvents.DismissDatePicker -> homeViewModel.updateDatePickerDialog(
                isDatePickerShowing = false
            )

            is HomeScreenEvents.SelectDateFromDatePicker -> {
                homeViewModel.getSelectedDate(events.userSelectedDate)
                // dismissing dialog on date selection
                homeViewModel.updateDatePickerDialog(isDatePickerShowing = false)
                homeViewModel.showSnackBar(snackBarHostState = events.snackBarHostState)
            }

            is HomeScreenEvents.UpdateFilterDialog -> homeViewModel.updateFilterDialog(
                isFilterDialogShowing = events.isDialogShowing
            )

            HomeScreenEvents.DismissFilterExercisesDialog -> homeViewModel.updateFilterDialog(
                isFilterDialogShowing = false
            )

            is HomeScreenEvents.OnConfirmFilterExercisesDialog -> homeViewModel.filterWorkouts(
                events.filterList
            )
        }
    }

    private fun homeAppBarEvents(events: HomeAppBarEvents) {
        when (events) {
            HomeAppBarEvents.GetNextDate -> homeViewModel.getNextDate()
            HomeAppBarEvents.GetPreviousDate -> homeViewModel.getPreviousDate()
            is HomeAppBarEvents.ShowDatePickerDialog -> homeViewModel.updateDatePickerDialog(
                isDatePickerShowing = events.showDialog
            )

            is HomeAppBarEvents.ShowFilterDialog -> homeViewModel.updateFilterDialog(
                isFilterDialogShowing = events.showDialog
            )
        }
    }
    private suspend fun showSnackBar(
        snackBarHostState: SnackbarHostState,
        message: String,
        actionLabelId: Int? = null
    ) {
        snackBarHostState.showSnackbar(
            message = message,
            actionLabel = if (actionLabelId != null) getString(actionLabelId) else null,
            withDismissAction = true,
            duration = SnackbarDuration.Short
        )
    }
}

private fun navigateToDestination(
    navigationInterface: NavigationInterface,
    navController: NavController
) {
    when (navigationInterface) {
        NavigationInterface.NavigateToHome -> {
            navigationEvent(
                navigationInterface,
                navController = navController
            )
        }

        NavigationInterface.NavigateToWorkoutLibrary -> {
            navigationEvent(
                navigationInterface,
                navController = navController
            )
        }

        NavigationInterface.NavigateToWorkoutStatistics -> {
            navigationEvent(
                navigationInterface,
                navController = navController
            )
        }
    }
}
