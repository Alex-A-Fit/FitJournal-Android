package com.example.fitjournal.core.domain.mapper

import com.example.fitjournal.R
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.domain.usecase.workout.AdjustMandatoryTimeValuesUseCase
import com.example.fitjournal.core.presentation.model.WorkoutDetailsUiModel
import com.example.fitjournal.core.presentation.model.WorkoutPropertiesUiModel
import com.example.fitjournal.core.presentation.model.WorkoutUiModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.extensions.roundToTwoDecimalPlaces
import com.example.fitjournal.core.util.extensions.toIntOrZero
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType

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
            val adjustMandatoryTimeValueUseCase = AdjustMandatoryTimeValuesUseCase()
            var laps = 0.0
            var distance = 0.0
            var hours = 0
            var minutes = 0
            var seconds = 0
            val entireSession =
                when (val workoutSets = workoutDetailsModel.workoutPropertiesModel) {
                    is WorkoutPropertiesModel.CardioProps -> {
                        if (workoutSets.props.isNotEmpty()) {
                            workoutSets.props.forEach {
                                laps += it.laps ?: 0.0
                                distance += if (it.distanceType == CardioDistanceType.KILOMETERS) it.distance * 0.621371 else it.distance
                                hours += it.time.hours.toIntOrZero()
                                minutes += it.time.minutes.toIntOrZero()
                                seconds += it.time.seconds.toIntOrZero()
                            }
                            CardioModel(
                                distance = distance.roundToTwoDecimalPlaces(),
                                distanceType = CardioDistanceType.MILES,
                                time = TimeModel(
                                    hours = hours.toString(),
                                    minutes = minutes.toString(),
                                    seconds = seconds.toString()
                                ),
                                laps = if (laps == 0.0) null else laps
                            )
                        } else null
                    }

                    else -> null
                }
            if (entireSession != null) {
                entireSession.time = adjustMandatoryTimeValueUseCase(entireSession.time)
            }
            WorkoutUiModel(
                date = date,
                id = id,
                workoutDetailsUiModel = WorkoutDetailsUiModel(
                    name = workoutDetailsModel.name,
                    icon = workoutDetailsModel.icon ?: R.drawable.icon_sprinting_person,
                    workoutType = WorkoutTypeEnum.CARDIO,
                    exerciseCardModel = entireSession?.let {
                        WorkoutPropertiesUiModel(
                            time = entireSession.time,
                            laps = entireSession.laps,
                            distance = entireSession.distance,
                            distanceType = entireSession.distanceType
                        )
                    }
                )
            )
        }
    }
}
