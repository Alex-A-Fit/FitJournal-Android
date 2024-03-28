package com.example.fitjournal.statistics.domain.model

data class StatisticsWorkoutState(
    val searchBarValue: String = "",
    val didUserSelectWorkout: Boolean = true,
    val statisticsEvents: (StatisticsWorkoutEvents) -> Unit
)
