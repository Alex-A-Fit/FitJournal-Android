package com.example.fitjournal.statistics.presentation.components.uistate

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum
import com.example.fitjournal.statistics.domain.model.WorkoutAnalytics
import com.example.fitjournal.statistics.presentation.components.graphs.CalisthenicsGraphTitle
import com.example.fitjournal.statistics.presentation.components.graphs.CardioGraphTitle
import com.example.fitjournal.statistics.presentation.components.graphs.WeightTrainingGraphTitle
import com.example.fitjournal.statistics.presentation.components.tabs.StatisticsTabRow
import com.example.fitjournal.statistics.presentation.components.text.WorkoutNameTitle
import com.example.fitjournal.statistics.presentation.model.StatisticsEvents

@Composable
fun StatisticsSuccessScreen(
    modifier: Modifier = Modifier,
    workoutName: String,
    workoutTypeEnum: WorkoutTypeEnum,
    timeRangeEnum: TimeRangeEnum,
    currentlyViewedWorkoutStats: WorkoutAnalytics?,
    statisticsClickEvents: (StatisticsEvents) -> Unit
) {
    val timeRangeOfWorkouts by rememberSaveable(timeRangeEnum) {
        mutableStateOf(timeRangeEnum)
    }
    Column(modifier = modifier) {
        WorkoutNameTitle(workoutName = workoutName)
        StatisticsTabRow(
            timeRangeOfWorkouts = timeRangeOfWorkouts.ordinal,
            getStatsBasedOnTimeSelected = {
                statisticsClickEvents(StatisticsEvents.UpdateTimeRange(it))
            }
        )
        when (workoutTypeEnum) {
            WorkoutTypeEnum.WEIGHT_TRAINING -> WeightTrainingGraphTitle(timeRangeEnum = timeRangeEnum)
            WorkoutTypeEnum.CALISTHENICS -> CalisthenicsGraphTitle(timeRangeEnum = timeRangeEnum)
            WorkoutTypeEnum.CARDIO -> CardioGraphTitle(timeRangeEnum = timeRangeEnum)
        }
    }
}
