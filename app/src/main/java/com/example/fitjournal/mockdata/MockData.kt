package com.example.fitjournal.mockdata

import com.example.fitjournal.R
import com.example.fitjournal.managers.DateManager
import com.example.fitjournal.model.domain.CalisthenicsModel
import com.example.fitjournal.model.domain.CardioModel
import com.example.fitjournal.model.domain.WeightLiftingModel
import com.example.fitjournal.model.domain.WorkoutModel
import com.example.fitjournal.model.enum.CardioDistanceType
import com.example.fitjournal.model.enum.WorkoutTypeEnum
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
            name = "Bench Press",
            icon = R.drawable.icon_dumbell,
            workoutTypeEnum = WorkoutTypeEnum.WEIGHT_TRAINING,
            timestamp = LocalDateTime.now(),
            date = todayDate,
            weightLiftingModel = listOf(
                WeightLiftingModel(
                    reps = 10,
                    weight = 135.0,
                    time = null
                )
            ),
            cardioModel = null,
            calisthenicsModel = null
        ),
        WorkoutModel(
            name = "Biking",
            icon = R.drawable.icon_sprinting_person,
            workoutTypeEnum = WorkoutTypeEnum.CARDIO,
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
}
