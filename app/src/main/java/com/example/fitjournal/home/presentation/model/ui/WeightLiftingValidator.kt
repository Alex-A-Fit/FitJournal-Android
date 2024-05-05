package com.example.fitjournal.home.presentation.model.ui

data class WeightLiftingValidator(
    val isRepsValid: Boolean,
    val isSetsValid: Boolean,
    val isWeightValid: Boolean
) {
    fun isWorkoutValid() = isRepsValid && isSetsValid && isWeightValid
}
