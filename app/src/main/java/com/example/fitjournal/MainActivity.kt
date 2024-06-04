package com.example.fitjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitjournal.addWorkout.screen.addworkout.AddWorkoutScreen
import com.example.fitjournal.addWorkout.screen.addworkout.AddWorkoutViewModel
import com.example.fitjournal.addWorkout.screen.addworkout.details.AddWorkoutDetailScreen
import com.example.fitjournal.addWorkout.screen.addworkout.details.AddWorkoutDetailViewModel
import com.example.fitjournal.core.presentation.commoncomponents.appbars.TopAppBar
import com.example.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons.NavigateUpIconButton
import com.example.fitjournal.core.presentation.navigation.Arguments
import com.example.fitjournal.core.presentation.navigation.NavigationInterface
import com.example.fitjournal.core.presentation.navigation.Route
import com.example.fitjournal.core.presentation.navigation.Route.LOTTIE_INTRO
import com.example.fitjournal.core.presentation.navigation.navigationEvent
import com.example.fitjournal.core.presentation.screens.AppScreen
import com.example.fitjournal.core.presentation.screens.lottie.LottieHomeScreenAnimation
import com.example.fitjournal.core.presentation.theme.FitJournalTheme
import com.example.fitjournal.home.presentation.components.appbar.EditWorkoutTopAppBar
import com.example.fitjournal.home.presentation.components.appbar.HomeTopAppBar
import com.example.fitjournal.home.presentation.model.events.HomeScreenEvents
import com.example.fitjournal.home.presentation.screen.editworkout.EditWorkoutScreen
import com.example.fitjournal.home.presentation.screen.editworkout.EditWorkoutViewModel
import com.example.fitjournal.home.presentation.screen.home.HomeScreen
import com.example.fitjournal.home.presentation.screen.home.HomeScreenViewModel
import com.example.fitjournal.library.presentation.screen.library.LibraryScreen
import com.example.fitjournal.library.presentation.screen.library.LibraryScreenViewModel
import com.example.fitjournal.statistics.presentation.screen.stats.StatisticsScreen
import com.example.fitjournal.statistics.presentation.screen.stats.StatisticsViewModel
import com.example.fitjournal.statistics.presentation.screen.statsdetails.StatisticsDetailsScreen
import com.example.fitjournal.statistics.presentation.screen.statsdetails.StatisticsDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeScreenViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.getDataFromRealm(
            getDataFromRealmForHomeScreen = {
                homeViewModel.homeScreenState.homeScreenEvents(
                    HomeScreenEvents.CollectRealmWorkoutEntryFromDb
                )
            }
        )
        setContent {
            FitJournalTheme {
                val navController = rememberNavController()
                val snackBarState = remember { SnackbarHostState() }
                var showChildFabs by remember { mutableStateOf(false) }
                val bottomBarVisibility = remember { (mutableStateOf(true)) }
                val homeScreenListState = rememberLazyListState()
                val libraryScreenListState = rememberLazyListState()

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
                            LaunchedEffect(Unit) {
                                bottomBarVisibility.value = true
                            }
                            val libraryScreenViewModel = hiltViewModel<LibraryScreenViewModel>()
                            AppScreen(
                                showChildrenFabIcons = showChildFabs,
                                modifier = Modifier,
                                mainScreen = { mainScreenModifier ->
                                    LibraryScreen(
                                        modifier = mainScreenModifier,
                                        libraryWorkoutState = libraryScreenViewModel.libraryWorkoutState,
                                        isBlurActive = showChildFabs,
                                        libraryScreenListState = libraryScreenListState,
                                        removeBlur = {
                                            showChildFabs = !it
                                        },
                                        showSnackbar = {
                                            showSnackBar(
                                                snackBarHostState = snackBarState,
                                                message = it
                                            )
                                        },
                                        navigateToDestination = {
                                            navigateToDestination(it, navController)
                                        }
                                    )
                                },
                                snackBarHostState = snackBarState,
                                topAppBar = {
                                    TopAppBar(
                                        appBarTitle = {
                                            Text(
                                                text = "Workout Library",
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    showChildFabs = false
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                },
                                displayChildFabs = {
                                    showChildFabs = it
                                },
                                addWorkoutToLibraryItemDatabase = { addWorkoutToDbModel ->
                                    mainViewModel.addWorkoutToLibraryDatabase(
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
                                            libraryScreenViewModel.getDataFromRealmDb()
                                        },
                                        errorCallback = {
                                            showSnackBar(
                                                snackBarHostState = snackBarState,
                                                message = getString(
                                                    R.string.error_with_workout_being_added_to_library,
                                                    addWorkoutToDbModel.workoutName
                                                )
                                            )
                                        },
                                        workoutType = getString(addWorkoutToDbModel.workoutType.stringId)
                                    )
                                },
                                navController = navController,
                                bottomBarVisibility = bottomBarVisibility.value
                            )
                        }
                        composable(Route.HOME_SCREEN) {
                            LaunchedEffect(Unit) {
                                bottomBarVisibility.value = true
                            }
                            AppScreen(
                                showChildrenFabIcons = showChildFabs,
                                modifier = Modifier,
                                snackBarHostState = snackBarState,
                                topAppBar = {
                                    HomeTopAppBar(
                                        currentDate = homeViewModel.homeScreenState.currentDate,
                                        homeAppBarEvents = homeViewModel.homeScreenState.homeAppBarEvents
                                    )
                                },
                                mainScreen = { mainScreenModifier ->
                                    HomeScreen(
                                        modifier = mainScreenModifier.fillMaxSize(),
                                        homeScreenState = homeViewModel.homeScreenState,
                                        isBlurActive = showChildFabs,
                                        lazyListState = homeScreenListState,
                                        navigateToDestination = { navInterface ->
                                            navigateToDestination(
                                                navigationInterface = navInterface,
                                                navController = navController
                                            )
                                        },
                                        removeBlur = {
                                            showChildFabs = false
                                        },
                                        showSnackBar = {
                                            showSnackBar(
                                                snackBarHostState = snackBarState,
                                                message = it
                                            )
                                        }
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    showChildFabs = false
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                },
                                displayChildFabs = {
                                    showChildFabs = it
                                },
                                addWorkoutToLibraryItemDatabase = { addWorkoutToDbModel ->
                                    mainViewModel.addWorkoutToLibraryDatabase(
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
                                        },
                                        errorCallback = {
                                            showSnackBar(
                                                snackBarHostState = snackBarState,
                                                message = getString(
                                                    R.string.error_with_workout_being_added_to_library,
                                                    addWorkoutToDbModel.workoutName
                                                )
                                            )
                                        },
                                        workoutType = getString(addWorkoutToDbModel.workoutType.stringId)
                                    )
                                },
                                navController = navController,
                                bottomBarVisibility = bottomBarVisibility.value
                            )
                        }
                        composable(Route.WORKOUT_STATISTICS_SCREEN) {
                            val statisticsViewModel = hiltViewModel<StatisticsViewModel>()
                            LaunchedEffect(Unit) {
                                bottomBarVisibility.value = true
                                statisticsViewModel.getDataFromRealmDb()
                            }
                            AppScreen(
                                showMainFabIcon = false,
                                modifier = Modifier,
                                snackBarHostState = snackBarState,
                                mainScreen = { mainScreenModifier ->
                                    StatisticsScreen(
                                        statisticsUiState = statisticsViewModel.statisticsUiState,
                                        modifier = mainScreenModifier.fillMaxSize(),
                                        navigateToDestination = {
                                            navigateToDestination(
                                                navigationInterface = it,
                                                navController = navController
                                            )
                                        }
                                    )
                                },
                                topAppBar = {
                                    TopAppBar(
                                        appBarTitle = {
                                            Text(
                                                text = stringResource(id = R.string.title_workout_statistics),
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    showChildFabs = false
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                },
                                navController = navController
                            )
                        }
                        composable("${Route.WORKOUT_STATISTICS_DETAILS_SCREEN}${Arguments.WORKOUT_NAME}") { backStackEntry ->
                            val workoutName =
                                backStackEntry.arguments?.getString("workoutName", "") ?: ""
                            val statisticsDetailsViewModel =
                                hiltViewModel<StatisticsDetailsViewModel>()
                            LaunchedEffect(Unit) {
                                bottomBarVisibility.value = false
                                statisticsDetailsViewModel.getDataFromRealmDb(
                                    workoutName = workoutName
                                )
                            }
                            AppScreen(
                                showMainFabIcon = false,
                                modifier = Modifier,
                                snackBarHostState = snackBarState,
                                mainScreen = { mainScreenModifier ->
                                    StatisticsDetailsScreen(
                                        statisticsUiState = statisticsDetailsViewModel.statisticsDetailsUiState,
                                        modifier = mainScreenModifier.fillMaxSize(),
                                        navigateToDestination = {
                                            navigateToDestination(
                                                navigationInterface = it,
                                                navController = navController
                                            )
                                        }
                                    )
                                },
                                topAppBar = {
                                    TopAppBar(
                                        appBarTitle = {
                                            Text(
                                                text = stringResource(id = R.string.title_workout_statistics),
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        },
                                        navigationIcon = {
                                            NavigateUpIconButton(
                                                navigateUp = { navController.navigateUp() }
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    showChildFabs = false
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                },
                                navController = navController
                            )
                        }
                        composable("${Route.ADD_WORKOUT_SCREEN}${Arguments.WORKOUT_DATE}") { backStackEntry ->
                            val addWorkoutViewModel = hiltViewModel<AddWorkoutViewModel>()
                            LaunchedEffect(Unit) {
                                bottomBarVisibility.value = false
                                addWorkoutViewModel.getDataFromRealmDb()
                            }
                            AppScreen(
                                showMainFabIcon = false,
                                modifier = Modifier,
                                snackBarHostState = snackBarState,
                                mainScreen = { mainModifier ->
                                    AddWorkoutScreen(
                                        modifier = mainModifier,
                                        addWorkoutUiState = addWorkoutViewModel.addWorkoutUiState,
                                        navigateToDestination = {
                                            showChildFabs = false
                                            navigateToDestination(
                                                navigationInterface = it,
                                                navController = navController
                                            )
                                        },
                                        workoutDate = backStackEntry.arguments?.getString("workoutDate")
                                            ?: ""
                                    )
                                },
                                topAppBar = {
                                    TopAppBar(
                                        appBarTitle = {
                                            Text(
                                                text = stringResource(id = R.string.title_add_to_journal),
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        },
                                        navigationIcon = {
                                            NavigateUpIconButton(navigateUp = { navController.navigateUp() })
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                navigateToDestination = { },
                                navController = navController,
                                bottomBarVisibility = bottomBarVisibility.value
                            )
                        }
                        composable("${Route.ADD_WORKOUT_DETAILS_SCREEN}${Arguments.WORKOUT_NAME}${Arguments.WORKOUT_TYPE}${Arguments.WORKOUT_DATE}") { backStackEntry ->
                            val addWorkoutDetailViewModel =
                                hiltViewModel<AddWorkoutDetailViewModel>()
                            LaunchedEffect(Unit) {
                                bottomBarVisibility.value = false
                                addWorkoutDetailViewModel.addNavigationArguments(
                                    workoutName = backStackEntry.arguments?.getString("workoutName")
                                        ?: "",
                                    workoutType = backStackEntry.arguments?.getString("workoutType")
                                        ?: "",
                                    workoutDate = backStackEntry.arguments?.getString("workoutDate")
                                        ?: ""
                                )
                            }
                            AppScreen(
                                showMainFabIcon = false,
                                modifier = Modifier,
                                snackBarHostState = snackBarState,
                                mainScreen = { mainModifier ->
                                    AddWorkoutDetailScreen(
                                        modifier = mainModifier,
                                        addWorkoutDetailUiState = addWorkoutDetailViewModel.addWorkoutDetailUiState,
                                        showSnackbar = {
                                            showSnackBar(
                                                snackBarHostState = snackBarState,
                                                message = it
                                            )
                                        },
                                        navigateBackToAddWorkoutScreen = {
                                            navController.navigateUp()
                                        },
                                        navigateBackToJournal = {
                                            navController.navigateUp()
                                            navController.navigateUp()

                                            lifecycleScope.launch {
                                                showSnackBar(
                                                    snackBarHostState = snackBarState,
                                                    message = it
                                                )
                                            }
                                        }
                                    )
                                },
                                topAppBar = {
                                    TopAppBar(
                                        appBarTitle = {
                                            Text(
                                                text = stringResource(id = R.string.title_add_to_journal),
                                                style = MaterialTheme.typography.titleLarge,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                        },
                                        navigationIcon = {
                                            NavigateUpIconButton(
                                                navigateUp = { navController.navigateUp() }
                                            )
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                },
                                navigateToDestination = {
                                    navigationEvent(
                                        navigationInterface = it,
                                        navController = navController
                                    )
                                },
                                navController = navController,
                                bottomBarVisibility = bottomBarVisibility.value
                            )
                        }
                        composable(LOTTIE_INTRO) {
                            LottieHomeScreenAnimation(
                                navController = navController
                            )
                        }
                        composable("${Route.EDIT_JOURNAL_SCREEN}${Arguments.WORKOUT_ID}") { backStackEntry ->
                            val editWorkoutViewModel = hiltViewModel<EditWorkoutViewModel>()
                            LaunchedEffect(Unit) {
                                bottomBarVisibility.value = false
                                editWorkoutViewModel.getSingleWorkout(
                                    backStackEntry.arguments?.getString("workoutId")
                                )
                            }
                            AppScreen(
                                showChildrenFabIcons = showChildFabs,
                                modifier = Modifier,
                                snackBarHostState = snackBarState,
                                topAppBar = {
                                    EditWorkoutTopAppBar(navigateUp = { navController.navigateUp() })
                                },
                                mainScreen = { mainScreenModifier ->
                                    EditWorkoutScreen(
                                        modifier = mainScreenModifier,
                                        editWorkoutViewModel.editWorkoutUiState,
                                        navigateToJournal = {
                                            navController.navigateUp()
                                        },
                                        showSnackbar = {
                                            showSnackBar(
                                                snackBarHostState = snackBarState,
                                                message = it
                                            )
                                        }
                                    )
                                },
                                navigateToDestination = { navigation ->
                                    showChildFabs = false
                                    navigateToDestination(
                                        navigationInterface = navigation,
                                        navController = navController
                                    )
                                },
                                navController = navController,
                                bottomBarVisibility = bottomBarVisibility.value,
                                showMainFabIcon = false
                            )
                        }
                    }
                }
            }
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

fun navigateToDestination(
    navigationInterface: NavigationInterface,
    navController: NavController
) = navigationEvent(
    navigationInterface = navigationInterface,
    navController = navController
)
