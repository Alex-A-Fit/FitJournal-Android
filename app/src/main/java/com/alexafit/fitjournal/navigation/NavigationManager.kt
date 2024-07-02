package com.alexafit.fitjournal.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.addWorkout.screen.addworkout.AddWorkoutScreen
import com.alexafit.fitjournal.addWorkout.screen.addworkout.AddWorkoutViewModel
import com.alexafit.fitjournal.addWorkout.screen.addworkout.details.AddWorkoutDetailScreen
import com.alexafit.fitjournal.addWorkout.screen.addworkout.details.AddWorkoutDetailViewModel
import com.alexafit.fitjournal.core.presentation.commoncomponents.appbars.TopAppBar
import com.alexafit.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons.NavigateUpIconButton
import com.alexafit.fitjournal.core.presentation.commoncomponents.icons.HelpIcon
import com.alexafit.fitjournal.core.presentation.navigation.Arguments
import com.alexafit.fitjournal.core.presentation.navigation.NavigationDirectionInterface
import com.alexafit.fitjournal.core.presentation.navigation.Route
import com.alexafit.fitjournal.core.presentation.navigation.Route.LOTTIE_INTRO
import com.alexafit.fitjournal.core.presentation.navigation.navigationEvent
import com.alexafit.fitjournal.core.presentation.screens.AppScreen
import com.alexafit.fitjournal.core.presentation.screens.lottie.LottieHomeScreenAnimation
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.core.util.localdate.formatToCommonDate
import com.alexafit.fitjournal.home.presentation.components.appbar.EditWorkoutTopAppBar
import com.alexafit.fitjournal.home.presentation.components.appbar.HomeTopAppBar
import com.alexafit.fitjournal.home.presentation.components.datepicker.FitJournalDatePicker
import com.alexafit.fitjournal.home.presentation.screen.editworkout.EditWorkoutScreen
import com.alexafit.fitjournal.home.presentation.screen.editworkout.EditWorkoutViewModel
import com.alexafit.fitjournal.home.presentation.screen.home.HomeScreen
import com.alexafit.fitjournal.library.domain.model.AddWorkoutToLibraryModel
import com.alexafit.fitjournal.library.presentation.components.LibraryTopAppBar
import com.alexafit.fitjournal.library.presentation.screen.library.LibraryScreen
import com.alexafit.fitjournal.library.presentation.screen.library.LibraryScreenViewModel
import com.alexafit.fitjournal.onboarding.presentation.model.OnboardingSections
import com.alexafit.fitjournal.onboarding.presentation.screen.OnboardingScreen
import com.alexafit.fitjournal.onboarding.presentation.screen.OnboardingViewModel
import com.alexafit.fitjournal.statistics.presentation.screen.stats.StatisticsScreen
import com.alexafit.fitjournal.statistics.presentation.screen.stats.StatisticsViewModel
import com.alexafit.fitjournal.statistics.presentation.screen.statsdetails.StatisticsDetailsScreen
import com.alexafit.fitjournal.statistics.presentation.screen.statsdetails.StatisticsDetailsViewModel
import java.time.LocalDate

class NavigationManager(
    private val navController: NavController,
    private val snackBarState: SnackbarHostState,
    private val bottomBarVisibility: MutableState<Boolean>,
    private val showAds: MutableState<Boolean>,
    private val setChildFabsVisibility: (Boolean) -> Unit,
    private val showChildFabs: MutableState<Boolean>,
    private val homeScreenListState: LazyListState,
    private val libraryScreenListState: LazyListState,
    private val showSnackBar: (String) -> Unit,
    private val addWorkoutToLibraryItemDatabase: (
        AddWorkoutToLibraryModel,
        getDataFromRealmDb: () -> Unit
    ) -> Unit,
    private val isThisUserFirstTime: Boolean,
    private val persistentHomeScreenData: PersistentHomeScreenData
) {
    private fun navigateToDestination(
        navigationDirectionInterface: NavigationDirectionInterface,
        navController: NavController
    ) = navigationEvent(
        navigationDirectionInterface = navigationDirectionInterface,
        navController = navController
    )

    fun NavGraphBuilder.onboardingScreen() {
        composable(Route.ONBOARDING_SCREEN) {
            LaunchedEffect(key1 = Unit) {
                showAds.value = false
            }
            val onboardingViewModel = hiltViewModel<OnboardingViewModel>()
            AppScreen(
                modifier = Modifier,
                mainScreen = { mainScreenModifier ->
                    OnboardingScreen(
                        modifier = mainScreenModifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        setUserCompletedOnboarding = {
                            onboardingViewModel.setUserHasOnboarded()
                            navigateToDestination(
                                navigationDirectionInterface = NavigationDirectionInterface.NavigateToHome,
                                navController = navController
                            )
                        },
                        navigateOnboarding = {
                            onboardingViewModel.setOnboardingSection(it)
                        },
                        onboardSectionToDisplay = onboardingViewModel.onboardingSection.value,
                        bottomBarVisibility = {
                            bottomBarVisibility.value = it
                        }
                    )
                },
                snackBarHostState = snackBarState,
                topAppBar = {
                    when (onboardingViewModel.onboardingSection.value) {
                        OnboardingSections.Intro -> {}
                        OnboardingSections.JournalSection -> {
                            TopAppBar(
                                appBarTitle = {
                                    FitJournalDatePicker(
                                        modifier = Modifier,
                                        getPreviousDate = {},
                                        getNextDate = {},
                                        currentDate = LocalDate.now()
                                            .formatToCommonDate(),
                                        showDatePickerDialog = {}
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                endAlignedActionIcon = {
                                    HelpIcon(
                                        modifier = Modifier.size(Spacing.spacing32),
                                        contentDescription = stringResource(id = R.string.content_desc_home_screen_help_icon),
                                        onClick = {}
                                    )
                                }
                            )
                        }

                        OnboardingSections.LibrarySection -> {
                            LibraryTopAppBar()
                        }

                        OnboardingSections.StatsSection -> {
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
                        }

                        OnboardingSections.End -> {}
                    }
                },
                navigateToDestination = { navigation ->
                    setChildFabsVisibility(false)
                    navigateToDestination(
                        navigationDirectionInterface = navigation,
                        navController = navController
                    )
                },
                displayChildFabs = setChildFabsVisibility,
                navController = navController,
                bottomBarVisibility = bottomBarVisibility.value,
                showMainFabIcon = false,
                showAds = showAds.value
            )
        }
    }

    fun NavGraphBuilder.libraryScreen() {
        composable(Route.WORKOUT_LIBRARY_SCREEN) {
            LaunchedEffect(Unit) {
                bottomBarVisibility.value = true
            }
            val libraryScreenViewModel = hiltViewModel<LibraryScreenViewModel>()
            AppScreen(
                showChildrenFabIcons = showChildFabs.value,
                modifier = Modifier,
                mainScreen = { mainScreenModifier ->
                    LibraryScreen(
                        modifier = mainScreenModifier,
                        libraryWorkoutState = libraryScreenViewModel.libraryWorkoutState,
                        isBlurActive = showChildFabs.value,
                        libraryScreenListState = libraryScreenListState,
                        removeBlur = setChildFabsVisibility,
                        showSnackbar = showSnackBar,
                        navigateToDestination = {
                            navigateToDestination(it, navController)
                        }
                    )
                },
                snackBarHostState = snackBarState,
                topAppBar = {
                    LibraryTopAppBar()
                },
                navigateToDestination = { navigation ->
                    setChildFabsVisibility(false)
                    navigateToDestination(
                        navigationDirectionInterface = navigation,
                        navController = navController
                    )
                },
                displayChildFabs = setChildFabsVisibility,
                addWorkoutToLibraryItemDatabase = { addWorkoutToLibraryModel ->
                    addWorkoutToLibraryItemDatabase(
                        addWorkoutToLibraryModel,
                        libraryScreenViewModel::getDataFromRealmDb
                    )
                },
                navController = navController,
                bottomBarVisibility = bottomBarVisibility.value,
                showAds = showAds.value
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun NavGraphBuilder.homeScreen() {
        composable(Route.HOME_SCREEN) {
            LaunchedEffect(Unit) {
                bottomBarVisibility.value = true
            }
            AppScreen(
                showChildrenFabIcons = showChildFabs.value,
                modifier = Modifier,
                snackBarHostState = snackBarState,
                topAppBar = {
                    HomeTopAppBar(
                        currentDate = persistentHomeScreenData.uiState.currentDate,
                        homeAppBarEvents = persistentHomeScreenData.uiState.homeAppBarEvents
                    )
                },
                mainScreen = { mainScreenModifier ->
                    HomeScreen(
                        modifier = mainScreenModifier.fillMaxSize(),
                        homeScreenState = persistentHomeScreenData.uiState,
                        isBlurActive = showChildFabs.value,
                        lazyListState = homeScreenListState,
                        navigateToDestination = { navInterface ->
                            navigateToDestination(
                                navigationDirectionInterface = navInterface,
                                navController = navController
                            )
                        },
                        setChildFabsVisibility = setChildFabsVisibility,
                        showSnackBar = showSnackBar
                    )
                },
                navigateToDestination = { navigation ->
                    setChildFabsVisibility(false)
                    navigateToDestination(
                        navigationDirectionInterface = navigation,
                        navController = navController
                    )
                },
                displayChildFabs = setChildFabsVisibility,
                addWorkoutToLibraryItemDatabase = { addWorkoutToLibraryModel ->
                    addWorkoutToLibraryItemDatabase(
                        addWorkoutToLibraryModel,
                        persistentHomeScreenData.getWorkoutsFromDatabase
                    )
                },
                navController = navController,
                bottomBarVisibility = bottomBarVisibility.value,
                showAds = showAds.value
            )
        }
    }

    fun NavGraphBuilder.statisticsScreen() {
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
                                navigationDirectionInterface = it,
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
                    setChildFabsVisibility(false)
                    navigateToDestination(
                        navigationDirectionInterface = navigation,
                        navController = navController
                    )
                },
                navController = navController,
                showAds = showAds.value
            )
        }
    }

    fun NavGraphBuilder.statisticsDetailsScreen() {
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
                        statisticsDetailsUiState = statisticsDetailsViewModel.statisticsDetailsUiState,
                        modifier = mainScreenModifier.fillMaxSize()
                    )
                },
                topAppBar = {
                    TopAppBar(
                        appBarTitle = {
                            Text(
                                text = stringResource(id = R.string.title_workout_statistics_details),
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
                    setChildFabsVisibility(false)
                    navigateToDestination(
                        navigationDirectionInterface = navigation,
                        navController = navController
                    )
                },
                navController = navController,
                showAds = showAds.value
            )
        }
    }

    fun NavGraphBuilder.addWorkoutScreen() {
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
                            setChildFabsVisibility(false)
                            navigateToDestination(
                                navigationDirectionInterface = it,
                                navController = navController
                            )
                        },
                        workoutDate = backStackEntry.arguments?.getString("workoutDate")
                            ?: "",
                        showSnackBar = showSnackBar
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
                bottomBarVisibility = bottomBarVisibility.value,
                showAds = showAds.value
            )
        }
    }

    fun NavGraphBuilder.addWorkoutDetailsScreen() {
        composable("${Route.ADD_WORKOUT_DETAILS_SCREEN}${Arguments.WORKOUT_NAME}${Arguments.WORKOUT_TYPE}${Arguments.WORKOUT_DATE}") { backStackEntry ->
            val addWorkoutDetailViewModel = hiltViewModel<AddWorkoutDetailViewModel>()
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
                        showSnackbar = showSnackBar,
                        navigateBackToAddWorkoutScreen = {
                            navController.navigateUp()
                        },
                        navigateBackToJournal = {
                            navigateToDestination(
                                NavigationDirectionInterface.NavigateToHome,
                                navController
                            )
                            showSnackBar(it)
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
                        navigationDirectionInterface = it,
                        navController = navController
                    )
                },
                navController = navController,
                bottomBarVisibility = bottomBarVisibility.value,
                showAds = showAds.value
            )
        }
    }

    fun NavGraphBuilder.lottieIntroScreen() {
        composable(LOTTIE_INTRO) {
            LottieHomeScreenAnimation(
                navController = navController,
                isThisUserFirstTime = isThisUserFirstTime
            )
        }
    }

    fun NavGraphBuilder.editJournalScreen() {
        composable("${Route.EDIT_JOURNAL_SCREEN}${Arguments.WORKOUT_ID}") { backStackEntry ->
            val editWorkoutViewModel = hiltViewModel<EditWorkoutViewModel>()
            LaunchedEffect(Unit) {
                bottomBarVisibility.value = false
                editWorkoutViewModel.getSingleWorkout(
                    backStackEntry.arguments?.getString("workoutId")
                )
            }
            AppScreen(
                showChildrenFabIcons = showChildFabs.value,
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
                        showSnackBar = showSnackBar
                    )
                },
                navigateToDestination = { navigation ->
                    setChildFabsVisibility(false)
                    navigateToDestination(
                        navigationDirectionInterface = navigation,
                        navController = navController
                    )
                },
                navController = navController,
                bottomBarVisibility = bottomBarVisibility.value,
                showMainFabIcon = false,
                showAds = showAds.value
            )
        }
    }
}
