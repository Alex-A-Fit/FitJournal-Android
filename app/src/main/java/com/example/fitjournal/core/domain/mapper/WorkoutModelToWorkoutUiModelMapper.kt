package com.example.fitjournal.core.domain.mapper

import com.example.fitjournal.R
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.domain.usecase.workout.AdjustMandatoryTimeValuesUseCase
import com.example.fitjournal.core.domain.usecase.workout.AdjustTimeValuesUseCase
import com.example.fitjournal.core.presentation.model.WorkoutDetailsUiModel
import com.example.fitjournal.core.presentation.model.WorkoutPropertiesUiModel
import com.example.fitjournal.core.presentation.model.WorkoutUiModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.core.util.constants.Constants.KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR
import com.example.fitjournal.core.util.constants.Constants.KILOMETERS_TO_MILES_CONVERSION_FACTOR
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
                        var weight: Double
                        workoutSets.props.maxByOrNull { liftingModel ->
                            weight = if (liftingModel.weightType.stringConcatenatedValue == "kgs") {
                                liftingModel.weight.times(KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR)
                            } else {
                                liftingModel.weight
                            }
                            weight
                        }
                    }
                }

                else -> null
            }
            if (topSet != null) {
                if (topSet.weightType.stringConcatenatedValue == "kgs") {
                    topSet.weight = topSet.weight.times(KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR)
                }
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
                            weight = it.weight.roundToTwoDecimalPlaces(),
                            sets = it.sets
                        )
                    }
                )
            )
        }

        WorkoutTypeEnum.CALISTHENICS -> {
            val adjustTimeValueUseCase = AdjustTimeValuesUseCase()
            var reps = 0
            var sets = 0
            var hours = 0
            var minutes = 0
            var seconds = 0
            var weight = 0.0

            val entireSession =
                when (val workoutSets = workoutDetailsModel.workoutPropertiesModel) {
                    is WorkoutPropertiesModel.CalisthenicsProps -> {
                        if (workoutSets.props.isNotEmpty()) {
                            workoutSets.props.forEach {
                                reps += it.reps
                                sets += it.sets
                                hours += it.time?.hours?.toIntOrZero() ?: 0
                                minutes += it.time?.minutes?.toIntOrZero() ?: 0
                                seconds += it.time?.seconds?.toIntOrZero() ?: 0
                                // removing weight until a better statistic can be established
//                                weight += when (it.weightType.stringConcatenatedValue) {
//                                    "kgs" -> {
//                                        val newWeight = (it.weight?.times(KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR)) ?: 0.0
//                                        newWeight * it.reps * it.sets
//                                    }
//
//                                    "lbs" -> {
//                                        (it.weight ?: 0.0) * it.reps * it.sets
//                                    }
//
//                                    else -> 0.0
//                                }
                            }
                            CalisthenicsModel(
                                reps = reps,
                                sets = sets,
                                time = if (hours == 0 && minutes == 0 && seconds == 0) {
                                    null
                                } else {
                                    TimeModel(
                                        hours = hours.toString(),
                                        minutes = minutes.toString(),
                                        seconds = seconds.toString()
                                    )
                                },
                                weight = weight
                            )
                        } else {
                            null
                        }
                    }

                    else -> null
                }
            if (entireSession != null) {
                entireSession.time = adjustTimeValueUseCase(entireSession.time)
            }
            WorkoutUiModel(
                date = date,
                id = id,
                workoutDetailsUiModel = WorkoutDetailsUiModel(
                    name = workoutDetailsModel.name,
                    icon = workoutDetailsModel.icon ?: R.drawable.icon_person,
                    workoutType = WorkoutTypeEnum.CALISTHENICS,
                    exerciseCardModel = entireSession?.let {
                        WorkoutPropertiesUiModel(
                            reps = it.reps,
                            time = it.time,
                            weight = if (it.weight == 0.0 || it.weight == null) null else it.weight.roundToTwoDecimalPlaces(),
                            sets = it.sets
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
                                distance += if (it.distanceType == CardioDistanceType.KILOMETERS) it.distance.times(KILOMETERS_TO_MILES_CONVERSION_FACTOR) else it.distance
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
                        } else {
                            null
                        }
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
                            distance = entireSession.distance
                        )
                    }
                )
            )
        }
    }
}
