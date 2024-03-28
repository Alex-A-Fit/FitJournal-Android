package com.example.fitjournal.statistics.domain.model

sealed class StatisticsWorkoutEvents {
    data class UpdateSearchBar(val newSearchBarValue: String): StatisticsWorkoutEvents()
}