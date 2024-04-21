package com.example.fitjournal.core.data.util

import com.example.fitjournal.R
import com.example.fitjournal.core.data.model.realmdb.WorkoutProperties
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType

fun getWorkoutType(workoutType: String?): WorkoutTypeEnum {
    return when (workoutType) {
        "Weight Training" -> WorkoutTypeEnum.WEIGHT_TRAINING
        "Calisthenics" -> WorkoutTypeEnum.CALISTHENICS
        "Cardio" -> WorkoutTypeEnum.CARDIO
        else -> WorkoutTypeEnum.WEIGHT_TRAINING
    }
}

fun getWorkoutIcon(workoutType: String?): Int {
    val workoutTypeValue = getWorkoutType(workoutType = workoutType)
    return when (workoutTypeValue) {
        WorkoutTypeEnum.WEIGHT_TRAINING -> R.drawable.icon_dumbell
        WorkoutTypeEnum.CALISTHENICS -> R.drawable.icon_person
        WorkoutTypeEnum.CARDIO -> R.drawable.icon_sprinting_person
    }
}

fun getWorkoutProperties(
    workoutProps: WorkoutProperties,
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
