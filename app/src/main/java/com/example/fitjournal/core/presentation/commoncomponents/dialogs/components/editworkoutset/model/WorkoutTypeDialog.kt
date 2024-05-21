package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model

sealed class WorkoutTypeDialog {
    data class Cardio(val editWorkoutSetCardioModel: EditWorkoutSetCardioModel) : WorkoutTypeDialog()
    data class Calisthenics(val editWorkoutSetCalisthenicsModel: EditWorkoutSetCalisthenicsModel) : WorkoutTypeDialog()
    data class WeightLifting(val editWorkoutSetWeightLiftingModel: EditWorkoutSetWeightLiftingModel) : WorkoutTypeDialog()
    data object None : WorkoutTypeDialog()
}
