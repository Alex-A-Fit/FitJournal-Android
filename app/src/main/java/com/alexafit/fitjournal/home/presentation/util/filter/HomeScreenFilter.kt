package com.alexafit.fitjournal.home.presentation.util.filter

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.alexafit.fitjournal.core.domain.model.WorkoutModel
import com.alexafit.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import com.alexafit.fitjournal.home.presentation.model.ui.FilterWorkoutUiModel

object HomeScreenFilter {
    val filterList: SnapshotStateList<FilterWorkoutUiModel> = mutableStateListOf(
        FilterWorkoutUiModel(
            isWorkoutFilterSelected = true,
            exerciseType = WorkoutTypeEnum.CALISTHENICS
        ),
        FilterWorkoutUiModel(
            isWorkoutFilterSelected = true,
            exerciseType = WorkoutTypeEnum.WEIGHT_TRAINING
        ),
        FilterWorkoutUiModel(
            isWorkoutFilterSelected = true,
            exerciseType = WorkoutTypeEnum.CARDIO
        )
    )

    fun filterWorkoutByDate(
        workoutList: List<WorkoutModel>,
        dateToFilterBy: String
    ): List<WorkoutModel> {
        if (workoutList.isEmpty()) return emptyList()
        return workoutList.filter {
            it.date == dateToFilterBy
        }
    }

    fun filterWorkoutByEnumType(
        workoutList: List<WorkoutModel>,
        filterList: List<FilterWorkoutUiModel>
    ): List<WorkoutModel> {
        val newWorkoutList: MutableList<WorkoutModel> = mutableListOf()
        if (filterList.all { !it.isWorkoutFilterSelected }) return emptyList()
        if (filterList.all { it.isWorkoutFilterSelected }) return workoutList
        filterList.forEach { filter ->
            if (filter.isWorkoutFilterSelected) {
                val filteredList = workoutList.filter { workout ->
                    workout.workoutDetailsModel.workoutTypeEnum == filter.exerciseType
                }
                newWorkoutList.addAll(filteredList)
            }
        }
        return newWorkoutList.toList()
    }
}
