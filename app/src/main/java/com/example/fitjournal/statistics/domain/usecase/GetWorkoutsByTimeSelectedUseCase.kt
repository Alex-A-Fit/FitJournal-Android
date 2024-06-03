package com.example.fitjournal.statistics.domain.usecase

import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.util.HelperFunctions
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum
import com.example.fitjournal.statistics.domain.model.WorkoutsByTimeRange
import java.time.Duration
import java.time.LocalDate
import javax.inject.Inject

class GetWorkoutsByTimeSelectedUseCase @Inject constructor() {
    operator fun invoke(
        workoutList: List<WorkoutModel>
    ): WorkoutsByTimeRange {
        return WorkoutsByTimeRange(
            week = getListOfWorkoutsByTimeRange(
                timeRangeEnum = TimeRangeEnum.WEEK,
                workoutList = workoutList
            ),
            month = getListOfWorkoutsByTimeRange(
                timeRangeEnum = TimeRangeEnum.MONTH,
                workoutList = workoutList
            ),
            year = getListOfWorkoutsByTimeRange(
                timeRangeEnum = TimeRangeEnum.YEAR,
                workoutList = workoutList
            ),
            allTime = getListOfWorkoutsByTimeRange(
                timeRangeEnum = TimeRangeEnum.ALL_TIME,
                workoutList = workoutList
            ),
        )
    }

    private fun getListOfWorkoutsByTimeRange(
        timeRangeEnum: TimeRangeEnum,
        workoutList: List<WorkoutModel>
    ): List<WorkoutModel> {
        val todayDate = LocalDate.now()
        return workoutList.filter {
            val date = HelperFunctions.parseDate(it.date)
            val daysBetween = Duration.between(
                date.atStartOfDay(),
                todayDate.atStartOfDay()
            )
            when (timeRangeEnum) {
                TimeRangeEnum.WEEK -> {
                    daysBetween.toDays() <= 7
                }

                TimeRangeEnum.MONTH -> {
                    daysBetween.toDays() <= 30
                }

                TimeRangeEnum.YEAR -> daysBetween.toDays() <= 365

                TimeRangeEnum.ALL_TIME -> true
            }
        }
    }
}

