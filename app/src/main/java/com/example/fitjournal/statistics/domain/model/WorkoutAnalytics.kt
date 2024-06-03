package com.example.fitjournal.statistics.domain.model

import co.yml.charts.common.model.Point

typealias Reps = String
typealias Weight = String
typealias Distance = String
typealias Time = String
typealias Date = String

sealed class WorkoutAnalytics {
    data class WeightTraining(
        val graphData: GraphAnalytics,
        val personalRecord: PersonalRecordAnalytics,
        val bestDistinctWeightForReps: List<DistinctWeightForReps>
    ) : WorkoutAnalytics()

    data class Cardio(
        val graphData: GraphAnalytics,
        val personalRecord: PersonalRecordAnalytics,
        val bestDistinctDistanceForTime: List<DistinctDistanceForTime>
    ) : WorkoutAnalytics()

    data class Calisthenics(
        val graphData: GraphAnalytics,
        val personalRecord: PersonalRecordAnalytics
    ) : WorkoutAnalytics()
}

data class PersonalRecordAnalytics(
    val prByWeek: PersonalRecords?,
    val prByMonth: PersonalRecords?,
    val prByYear: PersonalRecords?,
    val prByAllTime: PersonalRecords?
)

data class GraphAnalytics(
    val graphDataByWeek: GraphData,
    val graphDataByMonth: GraphData,
    val graphDataByYear: GraphData,
    val graphDataAllTime: GraphData
)

sealed class PersonalRecords {
    data class WeightTrainingPersonalRecord(
        val weightLifted: String,
        val dateWeightLifted: String,
        val highestVolume: String,
        val dateTotalVolume: String
    ) : PersonalRecords()

    data class CardioPersonalRecord(
        val farthestDistance: String,
        val dateFarthestDistance: String,
        val topSpeed: String,
        val dateTopSpeed: String
    ) : PersonalRecords()

    data class CalisthenicsPersonalRecord(
        val mostReps: String,
        val dateMostReps: String,
        val mostWeightUsed: String?,
        val dateMostWeightUsed: String?,
        val bestTime: String?,
        val dateBestTime: String?
    ) : PersonalRecords()
}

sealed class GraphData {
    data class WeightTraining(
        // looking at top set and highest weight
        val topWeightToDate: List<Point>,
        // compare total volume(reps * sets * weight) to date
        val mostVolumeToDate: List<Point>
    ) : GraphData()

    data class Cardio(
        // compare distance to date
        val totalDistanceToDate: List<Point>,
        // compare speed (distance/ Time) to date
        val averageSpeedToDate: List<Point>
    ) : GraphData()

    data class Calisthenics(
        val totalRepsToDate: List<Point>,
        val totalTimeToDate: List<Point>? = null,
        val totalWeightUsedToDate: List<Point>? = null
    ) : GraphData()
}

data class DistinctWeightForReps(
    val weight: Double,
    val reps: Int,
    val date: String
)

data class DistinctDistanceForTime(
    val distance: Double,
    val time: Double,
    val date: String
)
