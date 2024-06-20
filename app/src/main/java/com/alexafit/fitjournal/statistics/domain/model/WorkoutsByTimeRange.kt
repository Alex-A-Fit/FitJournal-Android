package com.alexafit.fitjournal.statistics.domain.model

import com.alexafit.fitjournal.core.domain.model.WorkoutModel

data class WorkoutsByTimeRange(
    val week: List<WorkoutModel> = emptyList(),
    val month: List<WorkoutModel> = emptyList(),
    val year: List<WorkoutModel> = emptyList(),
    val allTime: List<WorkoutModel> = emptyList()
)
