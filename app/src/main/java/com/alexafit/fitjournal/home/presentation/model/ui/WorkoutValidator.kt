package com.alexafit.fitjournal.home.presentation.model.ui

data class WeightLiftingValidator(
    val isRepsValid: Boolean,
    val isSetsValid: Boolean,
    val isWeightValid: Boolean
) {
    fun isWorkoutValid() = isRepsValid && isSetsValid && isWeightValid
}

data class CalisthenicsValidator(
    val isRepsValid: Boolean,
    val isSetsValid: Boolean,
    val isWeightValid: Boolean,
    val isTimeValid: Boolean
) {
    fun isWorkoutValid() = isRepsValid && isSetsValid && isWeightValid && isTimeValid
}

data class CardioValidator(
    val isLapsValid: Boolean,
    val isDistanceValid: Boolean,
    val isTimeValid: Boolean
) {
    fun isWorkoutValid() = isLapsValid && isDistanceValid && isTimeValid
}
