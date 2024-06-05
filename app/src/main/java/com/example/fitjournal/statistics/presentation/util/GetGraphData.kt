package com.example.fitjournal.statistics.presentation.util

import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum
import com.example.fitjournal.statistics.domain.model.WorkoutAnalytics

fun getGraphData(
    workoutStats: WorkoutAnalytics,
    timeRangeOfWorkouts: TimeRangeEnum
): GraphData {
    return when (workoutStats) {
        is WorkoutAnalytics.Calisthenics -> {
            val data = workoutStats.graphData
            when (timeRangeOfWorkouts) {
                TimeRangeEnum.WEEK -> data.graphDataByWeek
                TimeRangeEnum.MONTH -> data.graphDataByMonth
                TimeRangeEnum.YEAR -> data.graphDataByYear
                TimeRangeEnum.ALL_TIME -> data.graphDataAllTime
            }
        }
        is WorkoutAnalytics.Cardio -> {
            val data = workoutStats.graphData
            when (timeRangeOfWorkouts) {
                TimeRangeEnum.WEEK -> data.graphDataByWeek
                TimeRangeEnum.MONTH -> data.graphDataByMonth
                TimeRangeEnum.YEAR -> data.graphDataByYear
                TimeRangeEnum.ALL_TIME -> data.graphDataAllTime
            }
        }
        is WorkoutAnalytics.WeightTraining -> {
            val data = workoutStats.graphData
            when (timeRangeOfWorkouts) {
                TimeRangeEnum.WEEK -> data.graphDataByWeek
                TimeRangeEnum.MONTH -> data.graphDataByMonth
                TimeRangeEnum.YEAR -> data.graphDataByYear
                TimeRangeEnum.ALL_TIME -> data.graphDataAllTime
            }
        }
    }
}
