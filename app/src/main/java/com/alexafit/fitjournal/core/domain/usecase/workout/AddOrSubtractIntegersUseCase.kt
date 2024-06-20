package com.alexafit.fitjournal.core.domain.usecase.workout

import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.util.extensions.toIntOrZero
import javax.inject.Inject

class AddOrSubtractIntegersUseCase @Inject constructor() {
    operator fun invoke(
        editWorkoutFunction: EditWorkoutFunction,
        value: String
    ): String {
        val DEFAULT_VALUE = "1"
        return when (editWorkoutFunction) {
            EditWorkoutFunction.ADD_VALUE -> {
                try {
                    val currentValue = value.toIntOrZero()
                    if (currentValue < 999) {
                        currentValue.plus(1).toString()
                    } else {
                        DEFAULT_VALUE
                    }
                } catch (e: Exception) {
                    DEFAULT_VALUE
                }
            }

            EditWorkoutFunction.SUBTRACT_VALUE -> {
                try {
                    val currentValue = value.toIntOrZero()
                    if (currentValue > 1) {
                        currentValue.minus(1).toString()
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
