package com.example.fitjournal.statistics.domain.model

import co.yml.charts.common.model.Point

typealias Weight = String
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
    val prByWeek: PersonalRecordType?,
    val prByMonth: PersonalRecordType?,
    val prByYear: PersonalRecordType?,
    val prByAllTime: PersonalRecordType?
)

data class GraphAnalytics(
    val graphDataByWeek: GraphData,
    val graphDataByMonth: GraphData,
    val graphDataByYear: GraphData,
    val graphDataAllTime: GraphData
)

sealed class PersonalRecordType {
    data class WeightTraining(
        val mostWeightPr: PersonalRecord?,
        val mostVolumePr: PersonalRecord?
    ) : PersonalRecordType()

    data class Cardio(
        val totalDistancePr: PersonalRecord?,
        val bestSpeedPr: PersonalRecord?
    ) : PersonalRecordType()

    data class Calisthenics(
        val totalRepsPr: PersonalRecord?,
        val totalWeightUsedPr: PersonalRecord?
    ) : PersonalRecordType()
}

data class PersonalRecord(
    val personalRecord: String,
    val personalRecordDate: String,
    val allTimePr: String = "",
    val allTimePrDate: String = ""
)

sealed class GraphData {
    data class WeightTraining(
        // looking at top set and highest weight
        val topWeightToDate: List<GraphValues>?,
        // compare total volume(reps * sets * weight) to date
        val mostVolumeToDate: List<GraphValues>?
    ) : GraphData()

    data class Cardio(
        // compare distance to date
        val totalDistanceToDate: List<GraphValues>?,
        // compare speed (distance/ Time) to date
        val averageSpeedToDate: List<GraphValues>?
    ) : GraphData()

    data class Calisthenics(
        val totalRepsToDate: List<GraphValues>?,
        val totalWeightUsedToDate: List<GraphValues>? = null
    ) : GraphData()
}

data class DistinctWeightForReps(
    val weight: String,
    val reps: Int,
    val date: String
)

data class DistinctDistanceForTime(
    val distance: Double,
    val time: String,
    val date: String
)

data class GraphValues(
    val point: Point,
    val date: String
)
