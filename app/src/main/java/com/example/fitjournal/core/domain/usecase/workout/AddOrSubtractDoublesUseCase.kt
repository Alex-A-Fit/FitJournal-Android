package com.example.fitjournal.core.domain.usecase.workout

import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.util.extensions.toDoubleOrZero
import javax.inject.Inject

class AddOrSubtractDoublesUseCase @Inject constructor() {
    operator fun invoke(
        editWorkoutFunction: EditWorkoutFunction,
        value: String,
        valueDifferential: Double = 1.0
    ): String {
        val DEFAULT_VALUE = "0.0"
        return when (editWorkoutFunction) {
            EditWorkoutFunction.ADD_VALUE -> {
                try {
                    val currentValue = value.toDoubleOrZero()
                    val projectedValue = currentValue.plus(valueDifferential)
                    if (projectedValue <= 9999.0) {
                        projectedValue.toString()
                    } else {
                        DEFAULT_VALUE
                    }
                } catch (e: Exception) {
                    DEFAULT_VALUE
                }
            }

            EditWorkoutFunction.SUBTRACT_VALUE -> {
                try {
                    val currentValue = value.toDoubleOrZero()
                    if (currentValue == 0.0) return currentValue.toString()
                    val projectedValue = currentValue.minus(valueDifferential)
                    if (projectedValue >= 0.0) {
                        projectedValue.toString()
                    } else {
                        DEFAULT_VALUE
                    }
                } catch (e: Exception) {
                    DEFAULT_VALUE
                }
            }
        }
    }
}
