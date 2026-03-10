package com.alexafit.fitjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.alexafit.fitjournal.core.presentation.theme.FitJournalTheme
import com.alexafit.fitjournal.home.presentation.screen.home.HomeScreenViewModel
import com.alexafit.fitjournal.library.domain.model.AddWorkoutToLibraryModel
import com.alexafit.fitjournal.navigation.FitJournalNavHost
import com.alexafit.fitjournal.navigation.PersistentHomeScreenData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getDataFromRealmDb()
        setContent {
            val isThisUserFirstTime by mainViewModel.isThisUserFirstTimeUsingApp.collectAsState()
            val snackBarState = remember { SnackbarHostState() }

            FitJournalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    FitJournalNavHost(
                        isThisUserFirstTime = isThisUserFirstTime,
                        showSnackBar = {
                            showSnackBar(
                                snackBarHostState = snackBarState,
                                message = it
                            )
                        },
                        addWorkoutToLibraryItemDatabase = { addWorkoutToLibraryModel, getDataFromRealmDb ->
                            addWorkoutToLibraryDatabase(
                                addWorkoutToLibraryModel = addWorkoutToLibraryModel,
                                getDataFromRealmDb = getDataFromRealmDb,
                                snackBarState = snackBarState
                            )
                        },
                        persistentHomeScreenData = PersistentHomeScreenData(
                            uiState = homeViewModel.homeScreenState,
                            getWorkoutsFromDatabase = homeViewModel::getDataFromRealmDb
                        ),
                        snackBarState = snackBarState
                    )
                }
            }
        }
    }

    private fun addWorkoutToLibraryDatabase(
        addWorkoutToLibraryModel: AddWorkoutToLibraryModel,
        getDataFromRealmDb: () -> Unit,
        snackBarState: SnackbarHostState
    ) {
        mainViewModel.addWorkoutToLibraryDatabase(
            workoutName = addWorkoutToLibraryModel.workoutName,
            workoutTypeEnum = addWorkoutToLibraryModel.workoutType,
            successCallback = {
                showSnackBar(
                    snackBarHostState = snackBarState,
                    message = getString(
                        addWorkoutToLibraryModel.snackBarMessageId,
                        addWorkoutToLibraryModel.workoutName
                    )
                )
                getDataFromRealmDb()
            },
            errorCallback = {
                showSnackBar(
                    snackBarHostState = snackBarState,
                    message = getString(
                        R.string.error_with_workout_being_added_to_library,
                        addWorkoutToLibraryModel.workoutName
                    )
                )
            },
            workoutType = getString(addWorkoutToLibraryModel.workoutType.stringId)
        )
    }

    private fun showSnackBar(
        snackBarHostState: SnackbarHostState,
        message: String,
        actionLabelId: Int? = null
    ) {
        mainViewModel.showSnackBar(
            snackBarHostState = snackBarHostState,
            message = message,
            actionLabelId = if (actionLabelId != null) getString(actionLabelId) else null
        )
    }
}
