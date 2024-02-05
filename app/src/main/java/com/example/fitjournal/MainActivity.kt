package com.example.fitjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitjournal.components.datepicker.FitJournalDatePicker
import com.example.fitjournal.components.navigation.TopAppBar
import com.example.fitjournal.model.events.HomeScreenEvents
import com.example.fitjournal.navigation.NavigationInterface
import com.example.fitjournal.navigation.Route
import com.example.fitjournal.navigation.Route.LOTTIE_INTRO
import com.example.fitjournal.navigation.navigationEvent
import com.example.fitjournal.screens.AppScreen
import com.example.fitjournal.screens.home.HomeScreen
import com.example.fitjournal.screens.home.HomeScreenViewModel
import com.example.fitjournal.screens.lottie.LottieHomeScreenAnimation
import com.example.fitjournal.theme.FitJournalTheme
import com.example.fitjournal.theme.Spacing
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeScreenViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.runSplashScreen()
        setContent {
            FitJournalTheme {
                val navController = rememberNavController()
                val snackState = remember { SnackbarHostState() }

                // A surface container using the 'background' color from the theme
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
                                modifier = Modifier,
                                snackBarHostState = snackState,
                                topAppBar = {
                                    TopAppBar(
                                        appBarTitle = { HomeScreenTitle() },
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                },
                                mainScreen = { mainScreenModifier ->
//                                    HomeScreen(
//                                        modifier = mainScreenModifier,
//                                        homeScreenState = homeViewModel.homeScreenState,
//                                        homeScreenEvents = ::homeScreenEvents,
//                                        snackBarHostState = snackState
//                                        )
                                },
                                navigateToDestination = { navigation ->
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                }
                            )
                        }
                        composable(Route.HOME_SCREEN) {
                            AppScreen(
                                modifier = Modifier,
                                snackBarHostState = snackState,
                                topAppBar = {
                                    TopAppBar(
                                        appBarTitle = {
                                            FitJournalDatePicker(modifier = Modifier,
                                                getPreviousDate = { homeViewModel.getPreviousDate() },
                                                getNextDate = { homeViewModel.getNextDate() },
                                                currentDate = homeViewModel.homeScreenState.currentDate,
                                                showDatePickerDialog = {
                                                    homeViewModel.showDatePickerDialog()
                                                }
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        endAlignedActionIcon = {
                                            IconButton(onClick = { /*TODO*/ }) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.ic_icon_filter),
                                                    contentDescription = "Icon to filter workout cards",
                                                    modifier = Modifier.size(Spacing.spacing32)
                                                )
                                            }
                                        }
                                    )
                                },
                                mainScreen = { mainScreenModifier ->
                                    HomeScreen(
                                        modifier = mainScreenModifier.fillMaxSize(),
                                        homeScreenState = homeViewModel.homeScreenState,
                                        homeScreenEvents = ::homeScreenEvents,
                                        snackBarHostState = snackState
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                }
                            )
                        }
                        composable(Route.WORKOUT_STATISTICS_SCREEN) {
                            AppScreen(
                                modifier = Modifier,
                                snackBarHostState = snackState,
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
                                        appBarTitle = { HomeScreenTitle() },
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                }
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
            HomeScreenEvents.DismissDatePicker -> homeViewModel.dismissDatePickerDialog()
            is HomeScreenEvents.SelectDateFromDatePicker -> {
                homeViewModel.getSelectedDate(events.userSelectedDate)
                homeViewModel.dismissDatePickerDialog()
                homeViewModel.showSnackBar(snackBarHostState = events.snackBarHostState)
            }
        }
    }
}

@Composable
fun HomeScreenTitle() {
    Text(
        text = "Journal",
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimary
    )
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