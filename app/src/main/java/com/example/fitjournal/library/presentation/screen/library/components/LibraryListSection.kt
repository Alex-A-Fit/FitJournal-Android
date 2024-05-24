package com.example.fitjournal.library.presentation.screen.library.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.commoncomponents.listHeader.CategoryHeader
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutUiModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryListSection(
    libraryWorkoutState: LibraryWorkoutUiModel,
    isBlurActive: Boolean,
    updateUi: Boolean,
    libraryScreenListState: LazyListState,
    modifier: Modifier = Modifier,
    showEditLibraryWorkoutDialog: () -> Unit,
    removeBlur: (Boolean) -> Unit,
) {
    val workoutLibraryList = remember(libraryWorkoutState.listOfSearchedWorkouts) {
        libraryWorkoutState.listOfSearchedWorkouts
    }
    LaunchedEffect(key1 = updateUi) {
        if (updateUi){
            workoutLibraryList[libraryWorkoutState.workoutItemDialogUiModel.workoutCategoryIndex].items.remove(libraryWorkoutState.workoutItemDialogUiModel.libraryWorkoutItem)
        }
    }

    LazyColumn(
        modifier = modifier,
        userScrollEnabled = !isBlurActive,
        state = libraryScreenListState
    ) {
        workoutLibraryList.forEachIndexed { index, category ->
            stickyHeader {
                CategoryHeader(text = category.name)
            }
            itemsIndexed(category.items) { libraryIndex, workout ->
                Column(modifier = modifier.padding(start = Spacing.spacing16)) {
                    ExerciseItem(
                        exercise = workout.workoutName,
                        showEditLibraryWorkoutDialog = showEditLibraryWorkoutDialog,
                        workoutOnClick = {
                            libraryWorkoutState.libraryWorkoutClickEvent(
                                LibraryWorkoutClickEvents.WorkoutItemClicked(
                                    libraryWorkoutItem = workout,
                                    workoutCategoryIndex = index
                                )
                            )
                        },
                        isBlurActive = isBlurActive,
                        removeBlur = removeBlur
                    )
                    if (libraryIndex != category.items.lastIndex) {
                        HorizontalDivider(
                            thickness = Spacing.spacing1,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(Spacing.spacing64)) }
    }
}
