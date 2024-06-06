package com.example.fitjournal.statistics.presentation.util

import com.example.fitjournal.statistics.domain.model.GraphData
import com.example.fitjournal.statistics.domain.model.PersonalRecord
import com.example.fitjournal.statistics.domain.model.PersonalRecordAnalytics
import com.example.fitjournal.statistics.domain.model.PersonalRecordType
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum
import com.example.fitjournal.statistics.domain.model.WorkoutAnalytics

private typealias AllTimePr = List<PersonalRecord>
private typealias CurrentTimeRangePr = List<PersonalRecord>

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

fun getPersonalRecordAnalytics(
    workoutStats: WorkoutAnalytics
): PersonalRecordAnalytics {
    return when (workoutStats) {
        is WorkoutAnalytics.Calisthenics -> workoutStats.personalRecord
        is WorkoutAnalytics.Cardio -> workoutStats.personalRecord
        is WorkoutAnalytics.WeightTraining -> workoutStats.personalRecord
    }
}

fun getCalisthenicsPersonalRecord(
    workoutStats: PersonalRecordType
): PersonalRecordType.Calisthenics? {
    return when (workoutStats) {
        is PersonalRecordType.Calisthenics -> workoutStats
        else -> null
    }
}

fun getCardioPersonalRecord(
    workoutStats: PersonalRecordType
): PersonalRecordType.Cardio? {
    return when (workoutStats) {
        is PersonalRecordType.Cardio -> workoutStats
        else -> null
    }
}

fun getWeightTrainingPersonalRecord(
    workoutStats: PersonalRecordType
): PersonalRecordType.WeightTraining? {
    return when (workoutStats) {
        is PersonalRecordType.WeightTraining -> workoutStats
        else -> null
    }
}
