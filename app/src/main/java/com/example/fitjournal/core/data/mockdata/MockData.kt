package com.example.fitjournal.core.data.mockdata

import com.example.fitjournal.R
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object MockData {
    private val currentDateTime: LocalDateTime = LocalDateTime.now()
    private val currentDate: String = currentDateTime.toLocalDate()
        .format(DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.US))
    private val todayDate = DateManager.formatDate(currentDate)

    val ListOfWorkouts = listOf(
        WorkoutModel(
            name = "Push Ups",
            icon = R.drawable.icon_person,
            workoutTypeEnum = WorkoutTypeEnum.CALISTHENICS,
            date = todayDate,
            workoutPropertiesModel = WorkoutPropertiesModel.CalisthenicsProps(
                listOf(
                    CalisthenicsModel(
                        reps = 20,
                        time = "3:50",
                        sets = 1,
                        weight = null
                    )
                )
            )

        ),
        WorkoutModel(
            name = "Bench Press",
            icon = R.drawable.icon_dumbell,
            workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING,
            date = todayDate,
            workoutPropertiesModel = WorkoutPropertiesModel.WeightLiftingProps(
                listOf(
                    WeightLiftingModel(
                        reps = 10,
                        weight = 135.0,
                        sets = 1
                    )
                )
            )
        ),
        WorkoutModel(
            name = "Biking",
            icon = R.drawable.icon_sprinting_person,
            workoutTypeEnum = WorkoutTypeEnum.CARDIO,
            date = todayDate,
            workoutPropertiesModel = WorkoutPropertiesModel.CardioProps(
                listOf(
                    CardioModel(
                        distance = 2.0,
                        distanceType = CardioDistanceType.KILOMETERS,
                        time = "3:40",
                        laps = 2.0
                    )
                )
            )
        ),
        WorkoutModel(
            name = "Push Ups",
            icon = R.drawable.icon_person,
            workoutTypeEnum = WorkoutTypeEnum.CALISTHENICS,
            date = todayDate,
            workoutPropertiesModel = WorkoutPropertiesModel.CalisthenicsProps(
                listOf(
                    CalisthenicsModel(
                        reps = 20,
                        time = "3:50",
                        sets = 1,
                        weight = null
                    )
                )
            )

        ),
        WorkoutModel(
            name = "Bench Press",
            icon = R.drawable.icon_dumbell,
            workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING,
            date = todayDate,
            workoutPropertiesModel = WorkoutPropertiesModel.WeightLiftingProps(
                listOf(
                    WeightLiftingModel(
                        reps = 10,
                        weight = 135.0,
                        sets = 1
                    )
                )
            )
        ),
        WorkoutModel(
            name = "Biking",
            icon = R.drawable.icon_sprinting_person,
            workoutTypeEnum = WorkoutTypeEnum.CARDIO,
            date = todayDate,
            workoutPropertiesModel = WorkoutPropertiesModel.CardioProps(
                listOf(
                    CardioModel(
                        distance = 2.0,
                        distanceType = CardioDistanceType.KILOMETERS,
                        time = "3:40",
                        laps = 2.0
                    )
                )
            )
        )
    )

    val libraryWorkoutList = listOf(
        "Bench",
        "Squats",
        "Lateral Raises",
        "Elevated Goblet Squats",
        "Burpees",
        "Bicep Curls",
        "Box Jumps",
        "Mountain Climbers",
        "Medicine Ball Slams",
        "Mountain Pose",
        "Modified Push-Ups",
        "Mason Twist",
        "Monkey Bars",
        "Modified Burpees",
        "Lunges",
        "Leg Press",
        "Lat Pulldowns",
        "Lying leg curls",
        "L-sit",
        "LandMine Twists"
    )
}
