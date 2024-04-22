package com.example.fitjournal.core.domain.mapper

import com.example.fitjournal.R
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import com.example.fitjournal.home.presentation.model.ui.CardUiModel
import com.example.fitjournal.home.presentation.model.ui.WorkoutUiModel

fun WorkoutModel.MapToWorkoutUiModel(): WorkoutUiModel {
    return when (workoutTypeEnum) {
        WorkoutTypeEnum.WEIGHT_TRAINING -> {
            val topSet = when (val workoutSets = workoutPropertiesModel) {
                is WorkoutPropertiesModel.WeightLiftingProps -> {
                    workoutSets.props.maxBy { liftingModel ->
                        liftingModel.weight
                    }
                }

                else -> null
            }
            WorkoutUiModel(
                workoutType = WorkoutTypeEnum.WEIGHT_TRAINING,
                exerciseCardModel = CardUiModel(
                    name = name,
                    icon = icon ?: R.drawable.icon_dumbell,
                    reps = topSet?.reps,
                    weight = topSet?.weight,
                    id = id
                ),
                date = date
            )
        }

        WorkoutTypeEnum.CALISTHENICS -> {
            val mostRecentSession = when (val workoutSets = workoutPropertiesModel) {
                is WorkoutPropertiesModel.CalisthenicsProps -> {
                    workoutSets.props.last()
                }

                else -> null
            }
            WorkoutUiModel(
                workoutType = WorkoutTypeEnum.CALISTHENICS,
                exerciseCardModel = CardUiModel(
                    name = name,
                    icon = icon ?: R.drawable.icon_person,
                    reps = mostRecentSession?.reps,
                    time = mostRecentSession?.time,
                    id = id
                ),
                date = date
            )
        }

        WorkoutTypeEnum.CARDIO -> {
            val mostRecentSession = when (val workoutSets = workoutPropertiesModel) {
                is WorkoutPropertiesModel.CardioProps -> {
                    workoutSets.props.last()
                }

                else -> null
            }
            WorkoutUiModel(
                workoutType = WorkoutTypeEnum.CARDIO,
                exerciseCardModel = CardUiModel(
                    name = name,
                    icon = icon ?: R.drawable.icon_sprinting_person,
                    time = mostRecentSession?.time,
                    laps = mostRecentSession?.laps,
                    distance = mostRecentSession?.distance,
                    distanceType = mostRecentSession?.distanceType
                        ?: CardioDistanceType.MILES,
                    id = id
                ),
                date = date
            )
        }
    }
}
