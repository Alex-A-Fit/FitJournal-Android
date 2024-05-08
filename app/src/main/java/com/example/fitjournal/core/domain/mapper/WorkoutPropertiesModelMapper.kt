package com.example.fitjournal.core.domain.mapper

import com.example.fitjournal.core.data.model.realmdb.workout.CalisthenicsSet
import com.example.fitjournal.core.data.model.realmdb.workout.CardioSet
import com.example.fitjournal.core.data.model.realmdb.workout.RealmWorkoutProperties
import com.example.fitjournal.core.data.model.realmdb.workout.StrengthTrainingSet
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import io.realm.kotlin.ext.realmListOf

fun mapRealmWorkoutPropsToWorkoutPropsModel(
    workoutProps: RealmWorkoutProperties,
    workoutType: WorkoutTypeEnum
): WorkoutPropertiesModel {
    return when (workoutType) {
        WorkoutTypeEnum.WEIGHT_TRAINING -> {
            WorkoutPropertiesModel.WeightLiftingProps(
                props = workoutProps.listOfWeightLiftingSets.map {
                    WeightLiftingModel(
                        reps = it.reps,
                        sets = it.sets,
                        weight = it.weight
                    )
                }
            )
        }

        WorkoutTypeEnum.CALISTHENICS -> {
            WorkoutPropertiesModel.CalisthenicsProps(
                props = workoutProps.listOfCalisthenicsSet.map {
                    CalisthenicsModel(
                        reps = it.reps,
                        sets = it.sets,
                        time = breakTimeStringIntoModel(it.time),
                        weight = it.weight
                    )
                }
            )
        }

        WorkoutTypeEnum.CARDIO -> {
            WorkoutPropertiesModel.CardioProps(
                props = workoutProps.listOfCardioSets.map {
                    CardioModel(
                        distance = it.distance,
                        distanceType = if (it.distanceType == "km") {
                            CardioDistanceType.KILOMETERS
                        } else {
                            CardioDistanceType.MILES
                        },
                        time = breakTimeStringIntoModel(it.time),
                        laps = it.laps
                    )
                }
            )
        }
    }
}

fun mapWorkoutPropsToRealmWorkoutProps(
    workoutProps: WorkoutPropertiesModel?,
    workoutType: WorkoutTypeEnum
): RealmWorkoutProperties? {
    if (workoutProps == null) return null
    return when (workoutType) {
        WorkoutTypeEnum.WEIGHT_TRAINING -> {
            RealmWorkoutProperties().apply {
                listOfWeightLiftingSets = when (workoutProps) {
                    is WorkoutPropertiesModel.WeightLiftingProps -> {
                        val workoutList = workoutProps.props.map {
                            StrengthTrainingSet().apply {
                                reps = it.reps
                                sets = it.sets
                                weight = it.weight
                            }
                        }
                        val realmList = realmListOf<StrengthTrainingSet>()
                        realmList.addAll(workoutList)
                        realmList
                    }

                    else -> realmListOf()
                }
            }
        }

        WorkoutTypeEnum.CALISTHENICS -> {
            RealmWorkoutProperties().apply {
                listOfCalisthenicsSet = when (workoutProps) {
                    is WorkoutPropertiesModel.CalisthenicsProps -> {
                        val workoutList = workoutProps.props.map {
                            val workoutTime = it.time
                            CalisthenicsSet().apply {
                                reps = it.reps
                                sets = it.sets
                                weight = it.weight
                                time = if (workoutTime == null) "" else "${workoutTime.hours}:${workoutTime.minutes}:${workoutTime.seconds}"
                            }
                        }
                        val realmList = realmListOf<CalisthenicsSet>()
                        realmList.addAll(workoutList)
                        realmList
                    }

                    else -> realmListOf()
                }
            }
        }

        WorkoutTypeEnum.CARDIO -> {
            RealmWorkoutProperties().apply {
                listOfCardioSets = when (workoutProps) {
                    is WorkoutPropertiesModel.CardioProps -> {
                        val workoutList = workoutProps.props.map {
                            CardioSet().apply {
                                distance = it.distance
                                distanceType = it.distanceType.stringValue
                                laps = it.laps
                                time = if (it.time == null) "" else "${it.time.hours}:${it.time.minutes}:${it.time.seconds}"
                            }
                        }
                        val realmList = realmListOf<CardioSet>()
                        realmList.addAll(workoutList)
                        realmList
                    }

                    else -> realmListOf()
                }
            }
        }
    }
}

private fun breakTimeStringIntoModel(time: String?): TimeModel? {
    if (time == null) return null
    val timeList = time.split(":")
    if (timeList.size != 3) return null
    return TimeModel(hours = timeList[0], minutes = timeList[1], seconds = timeList[2])
}
