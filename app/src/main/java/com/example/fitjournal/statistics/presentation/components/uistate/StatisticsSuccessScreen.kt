package com.example.fitjournal.statistics.presentation.components.uistate

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.statistics.domain.model.WorkoutAnalytics
import com.example.fitjournal.statistics.domain.model.WorkoutsByTimeRange
import com.example.fitjournal.statistics.presentation.components.tabs.StatisticsTabRow
import com.example.fitjournal.statistics.presentation.components.text.WorkoutNameTitle
import com.example.fitjournal.statistics.presentation.model.StatisticsEvents

@Composable
fun StatisticsSuccessScreen(
    modifier: Modifier = Modifier,
    workoutStatistics: List<WorkoutModel>,
    currentlyViewedWorkoutStats: WorkoutAnalytics?,
    statisticsClickEvents: (StatisticsEvents) -> Unit
) {
    Column(modifier = modifier) {
        WorkoutNameTitle(workoutName = workoutStatistics[0].workoutDetailsModel.name)
        StatisticsTabRow(
            getStatsBasedOnTimeSelected = {
                statisticsClickEvents(StatisticsEvents.UpdateTimeRange(it, workoutStatistics))
            }
        )
        Log.d("WorkoutStats", "$currentlyViewedWorkoutStats")
    }
}