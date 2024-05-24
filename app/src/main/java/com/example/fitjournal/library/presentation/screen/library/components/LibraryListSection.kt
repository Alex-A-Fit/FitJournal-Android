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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.commoncomponents.listHeader.CategoryHeader
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.library.presentation.screen.library.model.LibraryWorkoutClickEvents
import com.example.fitjournal.library.presentation.screen.library.model.WorkoutCategory

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryListSection(
    categories: SnapshotStateList<WorkoutCategory>,
    isBlurActive: Boolean,
    libraryScreenListState: LazyListState,
    showDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    removeBlur: (Boolean) -> Unit,
    workoutOnClick: (LibraryWorkoutClickEvents) -> Unit
) {
    val workoutLibraryList = remember(categories) {
        categories
    }

    LazyColumn(
        modifier = modifier,
        userScrollEnabled = !isBlurActive,
        state = libraryScreenListState
    ) {
        workoutLibraryList.forEach { category ->
            stickyHeader {
                CategoryHeader(text = category.name)
            }
            itemsIndexed(category.items) { index, workout ->
                Column(modifier = modifier.padding(start = Spacing.spacing16)) {
                    ExerciseItem(
                        exercise = workout.workoutName,
                        showDialog = showDialog,
                        workoutOnClick = workoutOnClick,
                        isBlurActive = isBlurActive,
                        removeBlur = removeBlur,
                        workoutTypeEnum = workout.workoutTypeEnum
                    )
                    if (index != category.items.lastIndex) {
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
