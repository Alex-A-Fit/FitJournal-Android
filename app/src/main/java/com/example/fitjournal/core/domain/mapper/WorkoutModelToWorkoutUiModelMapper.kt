package com.example.fitjournal.core.domain.mapper

import com.example.fitjournal.R
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.presentation.model.WorkoutDetailsUiModel
import com.example.fitjournal.core.presentation.model.WorkoutPropertiesUiModel
import com.example.fitjournal.core.presentation.model.WorkoutUiModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum

fun WorkoutModel.mapToWorkoutUiModel(): WorkoutUiModel {
    return when (workoutDetailsModel.workoutTypeEnum) {
        WorkoutTypeEnum.WEIGHT_TRAINING -> {
            val topSet = when (val workoutSets = workoutDetailsModel.workoutPropertiesModel) {
                is WorkoutPropertiesModel.WeightLiftingProps -> {
                    if (workoutSets.props.isEmpty()) {
                        null
                    } else {
                        workoutSets.props.maxBy { liftingModel ->
                            liftingModel.weight
                        }
                    }
                }

                else -> null
            }
            WorkoutUiModel(
                date = date,
                id = id,
                workoutDetailsUiModel = WorkoutDetailsUiModel(
                    name = workoutDetailsModel.name,
                    icon = workoutDetailsModel.icon ?: R.drawable.icon_dumbell,
                    workoutType = WorkoutTypeEnum.WEIGHT_TRAINING,
                    exerciseCardModel = topSet?.let {
                        WorkoutPropertiesUiModel(
                            reps = it.reps,
                            weight = it.weight,
                            sets = it.sets
                        )
                    }
                )
            )
        }

        WorkoutTypeEnum.CALISTHENICS -> {
            val mostRecentSession =
                when (val workoutSets = workoutDetailsModel.workoutPropertiesModel) {
                    is WorkoutPropertiesModel.CalisthenicsProps -> {
                        if (workoutSets.props.isNotEmpty()) {
                            workoutSets.props.last()
                        } else {
                            null
                        }
                    }

                    else -> null
                }
            WorkoutUiModel(
                date = date,
                id = id,
                workoutDetailsUiModel = WorkoutDetailsUiModel(
                    name = workoutDetailsModel.name,
                    icon = workoutDetailsModel.icon ?: R.drawable.icon_person,
                    workoutType = WorkoutTypeEnum.CALISTHENICS,
                    exerciseCardModel = mostRecentSession?.let {
                        WorkoutPropertiesUiModel(
                            reps = mostRecentSession.reps,
                            time = mostRecentSession.time,
                            weight = mostRecentSession.weight,
                            sets = mostRecentSession.sets
                        )
                    }
                )
            )
        }

        WorkoutTypeEnum.CARDIO -> {
            val mostRecentSession =
                when (val workoutSets = workoutDetailsModel.workoutPropertiesModel) {
                    is WorkoutPropertiesModel.CardioProps -> {
                        if (workoutSets.props.isNotEmpty()) {
                            workoutSets.props.last()
                        } else {
                            null
                        }
                    }

                    else -> null
                }
            WorkoutUiModel(
                date = date,
                id = id,
                workoutDetailsUiModel = WorkoutDetailsUiModel(
                    name = workoutDetailsModel.name,
                    icon = workoutDetailsModel.icon ?: R.drawable.icon_sprinting_person,
                    workoutType = WorkoutTypeEnum.CARDIO,
                    exerciseCardModel = mostRecentSession?.let {
                        WorkoutPropertiesUiModel(
                            time = mostRecentSession.time,
                            laps = mostRecentSession.laps,
                            distance = mostRecentSession.distance,
                            distanceType = mostRecentSession.distanceType
                        )
                    }
                )
            )
        }
    }
}
