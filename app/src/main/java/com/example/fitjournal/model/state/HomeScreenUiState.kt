package com.example.fitjournal.model.state

import com.example.fitjournal.model.domain.WorkoutModel
import com.example.fitjournal.model.enum.WorkoutTypeEnum
import com.example.fitjournal.model.ui.FilterWorkoutUiModel
import com.example.fitjournal.model.ui.WorkoutUiModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

data class HomeScreenUiState(
    val currentDateTime: LocalDateTime = LocalDateTime.now(),
    val currentDate: String = currentDateTime.toLocalDate()
        .format(DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.US)),
    val currentDateInMillis: Long = (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000),
    val isDatePickerDialogShowing: Boolean = false,
    val isFilterDialogShowing: Boolean = false,
    val filterDialogList: List<FilterWorkoutUiModel> = listOf(
        FilterWorkoutUiModel(
            isWorkoutSelected = false,
            exerciseType = WorkoutTypeEnum.CALISTHENICS
        ),
        FilterWorkoutUiModel(
            isWorkoutSelected = false,
            exerciseType = WorkoutTypeEnum.WEIGHT_TRAINING
        ),
        FilterWorkoutUiModel(
            isWorkoutSelected = false,
            exerciseType = WorkoutTypeEnum.CARDIO
        )
    ),
    val listOfWorkouts: List<WorkoutModel>? = null,
    val listOfVisibleWorkouts: List<WorkoutUiModel>? = null
)
