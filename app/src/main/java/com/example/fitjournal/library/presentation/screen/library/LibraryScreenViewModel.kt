package com.example.fitjournal.library.presentation.screen.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitjournal.R
import com.example.fitjournal.core.domain.model.WorkoutLibraryModel
import com.example.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.filter.searchForText
import com.example.fitjournal.library.domain.model.UpdateWorkoutLibraryModel
import com.example.fitjournal.library.presentation.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.model.LibraryWorkoutUiModel
import com.example.fitjournal.library.presentation.model.WorkoutItemDialogUiModel
import com.example.fitjournal.library.presentation.utils.mapToLibraryUiList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryScreenViewModel @Inject constructor(
    private val realmWorkoutLibraryUseCase: RealmWorkoutLibraryUseCase
) : ViewModel() {
    var libraryWorkoutState by mutableStateOf(
        LibraryWorkoutUiModel(
            libraryWorkoutClickEvent = ::handleLibraryWorkoutEvents
        )
    )
        private set

    init {
        getDataFromRealmDb()
    }

    private fun handleLibraryWorkoutEvents(event: LibraryWorkoutClickEvents) {
        when (event) {
            LibraryWorkoutClickEvents.ClearSearch -> {
                updateLibraryWorkoutState(
                    newLibraryWorkoutState = libraryWorkoutState.copy(
                        searchedTerm = "",
                        listOfSearchedWorkouts = libraryWorkoutState.masterWorkoutList.toMutableStateList()
                    )
                )
            }

            is LibraryWorkoutClickEvents.UpdateSearch -> {
                val filteredList = searchForText(
                    event.text,
                    libraryWorkoutState.masterWorkoutList
                )
                updateLibraryWorkoutState(
                    newLibraryWorkoutState = libraryWorkoutState.copy(
                        searchedTerm = event.text,
                        listOfSearchedWorkouts = filteredList.toMutableStateList()
                    )
                )
            }

            is LibraryWorkoutClickEvents.WorkoutItemClicked -> {
                updateLibraryWorkoutState(
                    newLibraryWorkoutState = libraryWorkoutState.copy(
                        workoutItemDialogUiModel = WorkoutItemDialogUiModel(
                            libraryWorkoutItem = event.libraryWorkoutItem,
                            workoutCategoryIndex = event.workoutCategoryIndex
                        )
                    )
                )
            }

            is LibraryWorkoutClickEvents.DeleteLibraryWorkout -> {
                val workoutName =
                    libraryWorkoutState.workoutItemDialogUiModel.libraryWorkoutItem.workoutName
                val libraryWorkoutItem =
                    libraryWorkoutState.workoutItemDialogUiModel.libraryWorkoutItem
                val categoryIndex =
                    libraryWorkoutState.workoutItemDialogUiModel.workoutCategoryIndex
                viewModelScope.launch {
                    val wasItemDeleted = realmWorkoutLibraryUseCase
                        .deleteLibraryItemFromRealmDbUseCase(workoutName)
                    if (wasItemDeleted) {
                        libraryWorkoutState.masterWorkoutList.removeAt(categoryIndex)
                        getDataFromRealmDb()
                        event.onSuccessCallback(
                            event.context.getString(
                                R.string.text_workout_successfully_deleted_from_library,
                                workoutName
                            )
                        )
                    } else {
                        event.onErrorCallback(
                            event.context.getString(
                                R.string.error_with_workout_being_deleted,
                                workoutName
                            )
                        )
                    }
                }
            }

            is LibraryWorkoutClickEvents.UpdateLibraryWorkout -> {
                val realmModel = UpdateWorkoutLibraryModel(
                    originalWorkoutName = libraryWorkoutState.workoutItemDialogUiModel.libraryWorkoutItem.workoutName,
                    newName = event.workoutName,
                    newWorkoutTypeEnum = event.context.getString(event.workoutTypeEnum.stringId)
                )
                viewModelScope.launch {
                    val wasItemUpdated = realmWorkoutLibraryUseCase.updateLibraryItemInRealmDbUseCase(realmModel)
                    if (wasItemUpdated) {
                        updateLibraryWorkoutState(
                            newLibraryWorkoutState = libraryWorkoutState.copy(
                                workoutItemDialogUiModel = libraryWorkoutState.workoutItemDialogUiModel.copy(
                                    newWorkoutName = event.workoutName,
                                    newWorkoutType = event.workoutTypeEnum
                                )
                            )
                        )
                        event.onSuccessCallback(
                            event.context.getString(R.string.text_workout_successfully_updated_in_library)
                        )
                        getDataFromRealmDb()
                    } else {
                        event.onErrorCallback(
                            event.context.getString(R.string.error_library_workout_update_failed)
                        )
                    }
                }
            }
            LibraryWorkoutClickEvents.SyncRealmWorkoutEntryFromDb -> {
                val shouldSyncOccur = realmWorkoutLibraryUseCase.syncRealmWorkoutLibraryUseCase()
                if (shouldSyncOccur) {
                    getDataFromRealmDb()
                }
            }

            is LibraryWorkoutClickEvents.AddWorkoutToLibrary -> {
                addWorkoutToLibraryDatabase(
                    workoutName = event.workout.workoutName,
                    workoutType = event.context.getString(event.workout.workoutType.stringId),
                    workoutTypeEnum = event.workout.workoutType,
                    successCallback = {
                        getDataFromRealmDb()
                        viewModelScope.launch {
                            event.showSnackBar(
                                event.context.getString(
                                    event.workout.snackBarMessageId,
                                    event.workout.workoutName
                                )
                            )
                        }
                    },
                    errorCallback = {
                        viewModelScope.launch {
                            event.showSnackBar(
                                event.context.getString(
                                    R.string.error_with_workout_being_added_to_library,
                                    event.workout.workoutName
                                )
                            )
                        }
                    }
                )
            }
        }
    }

    private fun updateLibraryWorkoutState(newLibraryWorkoutState: LibraryWorkoutUiModel) {
        libraryWorkoutState = newLibraryWorkoutState
    }

    fun getDataFromRealmDb() {
        viewModelScope.launch {
            val workoutList = realmWorkoutLibraryUseCase.getRealmWorkoutLibraryList()
            if (workoutList.isNotEmpty()) {
                val libraryList = workoutList.groupBy { it.name.first().uppercase() }.toSortedMap()
                val masterWorkoutList = mapToLibraryUiList(libraryList)
                updateLibraryWorkoutState(
                    newLibraryWorkoutState = libraryWorkoutState.copy(
                        masterWorkoutList = masterWorkoutList,
                        listOfSearchedWorkouts = if (libraryWorkoutState.searchedTerm.isEmpty()) {
                            masterWorkoutList.toMutableStateList()
                        } else {
                            searchForText(
                                libraryWorkoutState.searchedTerm,
                                libraryWorkoutState.masterWorkoutList
                            ).toMutableStateList()
                        }
                    )
                )
            }
        }
    }

    private fun addWorkoutToLibraryDatabase(
        workoutName: String,
        workoutType: String,
        workoutTypeEnum: WorkoutTypeEnum,
        successCallback: suspend () -> Unit,
        errorCallback: suspend () -> Unit
    ) {
        viewModelScope.launch {
            val wasLibraryItemAdded =
                realmWorkoutLibraryUseCase.addSingleLibraryItemToRealmDbUseCase(
                    workoutLibraryModel = WorkoutLibraryModel(
                        name = workoutName,
                        workoutType = workoutType,
                        workoutTypeEnum = workoutTypeEnum
                    )
                )
            if (wasLibraryItemAdded) {
                successCallback()
            } else {
                errorCallback()
            }
        }
    }
}
