package com.example.fitjournal.core.domain.mapper

import com.example.fitjournal.R
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.presentation.model.WorkoutDetailsUiModel
import com.example.fitjournal.core.presentation.model.WorkoutPropertiesUiModel
import com.example.fitjournal.core.presentation.model.WorkoutUiModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType

fun WorkoutModel.mapToWorkoutUiModel(): WorkoutUiModel {
    return when (workoutDetailsModel.workoutTypeEnum) {
        WorkoutTypeEnum.WEIGHT_TRAINING -> {
            val topSet = when (val workoutSets = workoutDetailsModel.workoutPropertiesModel) {
                is WorkoutPropertiesModel.WeightLiftingProps -> {
                    workoutSets.props.maxBy { liftingModel ->
                        liftingModel.weight
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
                    exerciseCardModel = WorkoutPropertiesUiModel(
                        reps = topSet?.reps,
                        weight = topSet?.weight,
                        sets = topSet?.sets
                    )
                )
            )
        }

        WorkoutTypeEnum.CALISTHENICS -> {
            val mostRecentSession = when (val workoutSets = workoutDetailsModel.workoutPropertiesModel) {
                is WorkoutPropertiesModel.CalisthenicsProps -> {
                    workoutSets.props.last()
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
                    exerciseCardModel = WorkoutPropertiesUiModel(
                        reps = mostRecentSession?.reps,
                        time = mostRecentSession?.time,
                        weight = mostRecentSession?.weight,
                        sets = mostRecentSession?.sets
                    )
                )
            )
        }

        WorkoutTypeEnum.CARDIO -> {
            val mostRecentSession = when (val workoutSets = workoutDetailsModel.workoutPropertiesModel) {
                is WorkoutPropertiesModel.CardioProps -> {
                    workoutSets.props.last()
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
                    exerciseCardModel = WorkoutPropertiesUiModel(
                        time = mostRecentSession?.time,
                        laps = mostRecentSession?.laps,
                        distance = mostRecentSession?.distance,
                        distanceType = mostRecentSession?.distanceType ?: CardioDistanceType.MILES
                    )
                )
            )
        }
    }
}
