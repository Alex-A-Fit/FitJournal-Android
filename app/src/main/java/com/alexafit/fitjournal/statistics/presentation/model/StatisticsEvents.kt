package com.alexafit.fitjournal.statistics.presentation.model

sealed class StatisticsEvents {
    data object ClearSearchBarFilter : StatisticsEvents()
    data class FilterSearchByWorkout(val workout: String) : StatisticsEvents()
}
