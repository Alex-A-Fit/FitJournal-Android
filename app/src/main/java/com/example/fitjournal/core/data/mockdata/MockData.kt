package com.example.fitjournal.core.data.mockdata

import com.example.fitjournal.R
import com.example.fitjournal.core.domain.managers.DateManager
import com.example.fitjournal.core.domain.model.CalisthenicsModel
import com.example.fitjournal.core.domain.model.CardioModel
import com.example.fitjournal.core.domain.model.WeightLiftingModel
import com.example.fitjournal.core.domain.model.WorkoutDetail
import com.example.fitjournal.core.domain.model.WorkoutModel
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
            workoutDetail = WorkoutDetail("Push Ups", WorkoutTypeEnum.CALISTHENICS),
            icon = R.drawable.icon_person,
            date = todayDate,
            weightLiftingModel = null,
            cardioModel = null,
            timestamp = LocalDateTime.now(),
            calisthenicsModel = listOf(
                CalisthenicsModel(
                    reps = 20,
                    time = "3:50"
                )
            )
        ),
        WorkoutModel(
            workoutDetail = WorkoutDetail("Bench Press", WorkoutTypeEnum.WEIGHT_TRAINING),
            icon = R.drawable.icon_dumbell,
            timestamp = LocalDateTime.now(),
            date = todayDate,
            weightLiftingModel = listOf(
                WeightLiftingModel(
                    reps = 10,
                    weight = 135.0,
                    time = null,
                    sets = 1
                )
            ),
            cardioModel = null,
            calisthenicsModel = null
        ),
        WorkoutModel(
            workoutDetail = WorkoutDetail("Biking", WorkoutTypeEnum.CARDIO),
            icon = R.drawable.icon_sprinting_person,
            timestamp = LocalDateTime.now(),
            date = todayDate,
            weightLiftingModel = null,
            cardioModel = listOf(
                CardioModel(
                    distance = 2.0,
                    distanceType = CardioDistanceType.KILOMETERS,
                    time = "3:40",
                    laps = 2.0
                )
            ),
            calisthenicsModel = null
        ),
        WorkoutModel(
            workoutDetail = WorkoutDetail("Push Ups", WorkoutTypeEnum.CALISTHENICS),
            icon = R.drawable.icon_person,
            date = todayDate,
            weightLiftingModel = null,
            cardioModel = null,
            timestamp = LocalDateTime.now(),
            calisthenicsModel = listOf(
                CalisthenicsModel(
                    reps = 20,
                    time = "3:50"
                )
            )
        ),
        WorkoutModel(
            workoutDetail = WorkoutDetail("Bench Press", WorkoutTypeEnum.WEIGHT_TRAINING),
            icon = R.drawable.icon_dumbell,
            timestamp = LocalDateTime.now(),
            date = todayDate,
            weightLiftingModel = listOf(
                WeightLiftingModel(
                    reps = 10,
                    weight = 135.0,
                    time = null,
                    sets = 12
                )
            ),
            cardioModel = null,
            calisthenicsModel = null
        ),
        WorkoutModel(
            workoutDetail = WorkoutDetail("Biking", WorkoutTypeEnum.CARDIO),
            icon = R.drawable.icon_sprinting_person,
            timestamp = LocalDateTime.now(),
            date = todayDate,
            weightLiftingModel = null,
            cardioModel = listOf(
                CardioModel(
                    distance = 2.0,
                    distanceType = CardioDistanceType.KILOMETERS,
                    time = "3:40",
                    laps = 2.0
                )
            ),
            calisthenicsModel = null
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
