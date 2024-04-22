package com.example.fitjournal.core.domain.mapper

import com.example.fitjournal.core.data.model.realmdb.CalisthenicsSet
import com.example.fitjournal.core.data.model.realmdb.CardioSet
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutProperties
import com.example.fitjournal.core.data.model.realmdb.StrengthTrainingSet
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
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
                        time = it.time,
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
                        distanceType = if (it.distanceType == "km") CardioDistanceType.KILOMETERS else CardioDistanceType.MILES,
                        time = it.time,
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
                            CalisthenicsSet().apply {
                                reps = it.reps
                                sets = it.sets
                                weight = it.weight
                                time = it.time
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
                                time = it.time
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
