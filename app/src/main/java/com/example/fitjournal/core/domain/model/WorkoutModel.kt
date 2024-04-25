package com.example.fitjournal.core.domain.model

import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

data class WorkoutModel(
    val id: String,
    val workoutDetailsModel: WorkoutDetailsModel,
    val date: String
)

data class WorkoutDetailsModel(
    val name: String,
    val icon: Int?,
    val workoutTypeEnum: WorkoutTypeEnum,
    val workoutPropertiesModel: WorkoutPropertiesModel? = null
)
