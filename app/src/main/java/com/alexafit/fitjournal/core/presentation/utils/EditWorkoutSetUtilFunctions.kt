package com.alexafit.fitjournal.core.presentation.utils

import androidx.core.text.isDigitsOnly
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.util.extensions.roundToTwoDecimalPlaces
import com.alexafit.fitjournal.core.util.extensions.toDoubleOrZero

typealias ShowError = Boolean
typealias NewValue = String

object EditWorkoutSetUtilFunctions {
    fun editRepsOrSets(
        editWorkoutFunction: EditWorkoutFunction,
        repsOrSetsValue: String
    ): Pair<ShowError, NewValue> {
        try {
            var newValue: NewValue = ""
            if (repsOrSetsValue.isDigitsOnly() && repsOrSetsValue != "0" && repsOrSetsValue.isNotEmpty()) {
                var setsInt = repsOrSetsValue.toIntOrNull() ?: return Pair(true, repsOrSetsValue)
                when (editWorkoutFunction) {
                    EditWorkoutFunction.ADD_VALUE -> setsInt += 1
                    EditWorkoutFunction.SUBTRACT_VALUE -> setsInt -= 1
                }
                newValue = setsInt.toString()
            } else if (repsOrSetsValue == "0" || repsOrSetsValue.isEmpty()) {
                newValue = when (editWorkoutFunction) {
                    EditWorkoutFunction.ADD_VALUE -> "1"
                    EditWorkoutFunction.SUBTRACT_VALUE -> "0"
                }
            }
            if (newValue == "0") return Pair(true, newValue)
            return Pair(false, newValue)
        } catch (e: Exception) {
            return Pair(true, repsOrSetsValue)
        }
    }

    fun editWeightOrDistance(
        editWorkoutFunction: EditWorkoutFunction,
        value: String,
        isValueOptional: Boolean
    ): Pair<ShowError, NewValue> {
        return try {
            val newValue: NewValue
            when {
                value.isEmpty() || value.toDoubleOrZero() == 0.0 -> {
                    newValue = when (editWorkoutFunction) {
                        EditWorkoutFunction.ADD_VALUE -> "1.0"
                        EditWorkoutFunction.SUBTRACT_VALUE -> "0"
                    }
                }

                value.last() == '.' -> {
                    val oldWeight = value.substringBefore(".")
                    newValue = try {
                        when (editWorkoutFunction) {
                            EditWorkoutFunction.ADD_VALUE -> (oldWeight.toDoubleOrZero() + 1).toString()
                            EditWorkoutFunction.SUBTRACT_VALUE -> (oldWeight.toDoubleOrZero() - 1).toString()
                        }
                    } catch (e: Exception) {
                        when (editWorkoutFunction) {
                            EditWorkoutFunction.ADD_VALUE -> "1.0"
                            EditWorkoutFunction.SUBTRACT_VALUE -> "0"
                        }
                    }
                }

                else -> {
                    newValue =
                        when (editWorkoutFunction) {
                            EditWorkoutFunction.ADD_VALUE -> (value.toDoubleOrZero() + 1).toString()
                            EditWorkoutFunction.SUBTRACT_VALUE -> {
                                val newWeight = value.toDoubleOrZero() - 1
                                when {
                                    newWeight <= 0 -> "0"
                                    else -> newWeight.roundToTwoDecimalPlaces().toString()
                                }
                            }
                        }
                }
            }
            if (newValue.toDoubleOrNull() == null) Pair(true, value)
            if (!isValueOptional && newValue == "0") Pair(true, value)
            Pair(false, newValue)
        } catch (e: Exception) {
            Pair(true, value)
        }
    }

    fun editLaps(
        editWorkoutFunction: EditWorkoutFunction,
        value: String
    ): Pair<ShowError, NewValue> {
        var newValue: NewValue = ""
        return try {
            if (value != "0" && value.isNotEmpty()) {
                var lapsDouble = value.toDoubleOrNull() ?: return Pair(true, value)
                when (editWorkoutFunction) {
                    EditWorkoutFunction.ADD_VALUE -> lapsDouble += 1.0
                    EditWorkoutFunction.SUBTRACT_VALUE -> lapsDouble -= 1.0
                }
                newValue =
                    if (lapsDouble < 0) "0" else lapsDouble.roundToTwoDecimalPlaces().toString()
            } else if (value == "0" || value.isEmpty()) {
                newValue = when (editWorkoutFunction) {
                    EditWorkoutFunction.ADD_VALUE -> "1"
                    EditWorkoutFunction.SUBTRACT_VALUE -> "0"
                }
            }
            if (newValue == "0") Pair(true, newValue)
            Pair(false, newValue)
        } catch (e: Exception) {
            Pair(true, value)
        }
    }
}
