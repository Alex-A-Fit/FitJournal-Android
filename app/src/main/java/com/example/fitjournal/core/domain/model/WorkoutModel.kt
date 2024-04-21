package com.example.fitjournal.core.domain.model

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class WorkoutModel(
    val name: String,
    val icon: Int?,
    val workoutTypeEnum: WorkoutTypeEnum,
    val date: String,
    val workoutPropertiesModel: WorkoutPropertiesModel? = null
)
