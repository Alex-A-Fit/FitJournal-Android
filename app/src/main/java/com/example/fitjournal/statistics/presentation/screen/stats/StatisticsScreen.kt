package com.example.fitjournal.statistics.presentation.screen.stats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.fitjournal.commoncomponents.textField.SearchBar
import com.example.fitjournal.statistics.domain.model.StatisticsWorkoutEvents
import com.example.fitjournal.statistics.domain.model.StatisticsWorkoutState
import com.example.fitjournal.statistics.presentation.section.WorkoutListSection
import com.example.fitjournal.theme.Spacing

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    statisticsWorkoutState: StatisticsWorkoutState,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.spacing16)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                keyboardController?.hide()
                focusManager.clearFocus(true)
            }
    ) {
        SearchBar(
            searchedTerm = statisticsWorkoutState.searchBarValue,
            updateSearchBarText = {
                statisticsWorkoutState
                    .statisticsEvents(
                        StatisticsWorkoutEvents.UpdateSearchBar(newSearchBarValue = it)
                    )
            },
            keyboardController = keyboardController,
            focusManager = focusManager
        )

        if (!statisticsWorkoutState.didUserSelectWorkout){
            WorkoutListSection(modifier = Modifier.padding(horizontal = Spacing.spacing16), listOfSearchedWorkouts = listOf())
        }
    }
}
