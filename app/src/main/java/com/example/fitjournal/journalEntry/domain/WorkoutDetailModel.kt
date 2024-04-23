package com.example.fitjournal.journalEntry.domain

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class WorkoutDetail(
    val workoutName: String,
    val workoutType: WorkoutTypeEnum
)
