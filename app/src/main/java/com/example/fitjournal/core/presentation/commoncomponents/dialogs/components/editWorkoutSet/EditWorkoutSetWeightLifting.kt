package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.components.EditWorkoutMandatoryWeightSection
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model.EditWorkoutSetWeightLiftingModel
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model.WorkoutTypeDialog
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.util.extensions.roundToTwoDecimalPlaces
import com.example.fitjournal.core.util.extensions.toDoubleOrZero
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

@Composable
fun EditWorkoutSetWeightLifting(
    editWorkoutSetWeightLiftingModel: EditWorkoutSetWeightLiftingModel,
    isWorkoutValid: (Boolean, WorkoutTypeDialog?) -> Unit
) {
    var repValue by rememberSaveable {
        mutableStateOf(editWorkoutSetWeightLiftingModel.reps)
    }
    var isRepErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var setValue by rememberSaveable {
        mutableStateOf(editWorkoutSetWeightLiftingModel.sets)
    }
    var isSetErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var weightValue by rememberSaveable {
        mutableStateOf(editWorkoutSetWeightLiftingModel.weight)
    }
    var isWeightErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var weightType by rememberSaveable {
        mutableStateOf(editWorkoutSetWeightLiftingModel.weightType)
    }
    LaunchedEffect(
        key1 = repValue,
        key2 = setValue,
        key3 = weightValue
    ) {
        if (isRepErrorVisible || isSetErrorVisible || isWeightErrorVisible) {
            isWorkoutValid(false, null)
        } else {
            isWorkoutValid(
                true,
                WorkoutTypeDialog.WeightLifting(
                    editWorkoutSetWeightLiftingModel = EditWorkoutSetWeightLiftingModel(
                        reps = repValue,
                        sets = setValue,
                        weight = weightValue,
                        weightType = weightType,
                        index = editWorkoutSetWeightLiftingModel.index
                    )
                )
            )
        }
    }
    LaunchedEffect(
        key1 = weightType
    ) {
        if (isRepErrorVisible || isSetErrorVisible || isWeightErrorVisible) {
            isWorkoutValid(false, null)
        } else {
            isWorkoutValid(
                true,
                WorkoutTypeDialog.WeightLifting(
                    editWorkoutSetWeightLiftingModel = EditWorkoutSetWeightLiftingModel(
                        reps = repValue,
                        sets = setValue,
                        weight = weightValue,
                        weightType = weightType,
                        index = editWorkoutSetWeightLiftingModel.index
                    )
                )
            )
        }
    }

    EditWorkoutSetsSection(
        isErrorVisible = isSetErrorVisible,
        editSetsValue = { editWorkoutFunction: EditWorkoutFunction, sets: String ->
            isSetErrorVisible = false
            try {
                if (sets.isDigitsOnly() && sets != "0" && sets.isNotEmpty()) {
                    var setsInt = sets.toIntOrNull()
                    if (setsInt == null) {
                        isSetErrorVisible = true
                        return@EditWorkoutSetsSection
                    }
                    when (editWorkoutFunction) {
                        EditWorkoutFunction.ADD_VALUE -> setsInt += 1
                        EditWorkoutFunction.SUBTRACT_VALUE -> setsInt -= 1
                    }
                    setValue = setsInt.toString()
                } else if (sets == "0" || sets.isEmpty()) {
                    setValue = when (editWorkoutFunction) {
                        EditWorkoutFunction.ADD_VALUE -> "1"
                        EditWorkoutFunction.SUBTRACT_VALUE -> "0"
                    }
                }
                if (setValue == "0") {
                    isSetErrorVisible = true
                }
            } catch (e: Exception) {
                isSetErrorVisible = true
            }
        },
        setsValue = setValue,
        onSetValueChange = {
            isSetErrorVisible = it.isBlank()
            setValue = it
        }
    )
    EditWorkoutRepsSection(
        isRepsErrorVisible = isRepErrorVisible,
        editRepValue = { editWorkoutFunction: EditWorkoutFunction, reps: String ->
            isRepErrorVisible = false
            try {
                if (reps.isDigitsOnly() && reps != "0" && reps.isNotEmpty()) {
                    var repsInt = reps.toIntOrNull()
                    if (repsInt == null) {
                        isRepErrorVisible = true
                        return@EditWorkoutRepsSection
                    }
                    when (editWorkoutFunction) {
                        EditWorkoutFunction.ADD_VALUE -> repsInt += 1
                        EditWorkoutFunction.SUBTRACT_VALUE -> repsInt -= 1
                    }
                    repValue = repsInt.toString()
                } else if (reps == "0" || reps.isEmpty()) {
                    repValue = when (editWorkoutFunction) {
                        EditWorkoutFunction.ADD_VALUE -> "1"
                        EditWorkoutFunction.SUBTRACT_VALUE -> "0"
                    }
                }
                if (repValue == "0") {
                    isRepErrorVisible = true
                }
            } catch (e: Exception) {
                isRepErrorVisible = true
            }
        },
        repValue = repValue,
        onRepValueChange = {
            isRepErrorVisible = it.isBlank()
            repValue = it
        }
    )
    EditWorkoutMandatoryWeightSection(
        isWeightErrorVisible = isWeightErrorVisible,
        poundsOrKilogramsText = weightType.stringValue,
        weightValue = weightValue,
        onWeightTypeClicked = {
            weightType = if (weightType == WeightLiftingWeightType.POUNDS) {
                WeightLiftingWeightType.KILOGRAMS
            } else {
                WeightLiftingWeightType.POUNDS
            }
        },
        onWeightValueChange = {
            isWeightErrorVisible = it.isBlank()
            weightValue = it
        },
        editWeightValue = { editWorkoutFunction: EditWorkoutFunction, weight: String ->
            isWeightErrorVisible = false
            try {
                when {
                    weight.isEmpty() || weight.toDoubleOrZero() == 0.0 -> {
                        weightValue = when (editWorkoutFunction) {
                            EditWorkoutFunction.ADD_VALUE -> "1.0"
                            EditWorkoutFunction.SUBTRACT_VALUE -> "0"
                        }
                    }

                    weight.last() == '.' -> {
                        val oldWeight = weight.substringBefore(".")
                        weightValue = try {
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
                        weightValue =
                            when (editWorkoutFunction) {
                                EditWorkoutFunction.ADD_VALUE -> (weight.toDoubleOrZero() + 1).toString()
                                EditWorkoutFunction.SUBTRACT_VALUE -> {
                                    val newWeight = weight.toDoubleOrZero() - 1
                                    when {
                                        newWeight <= 0 -> "0"
                                        else -> newWeight.roundToTwoDecimalPlaces().toString()
                                    }
                                }
                            }
                    }
                }
                if (weightValue.toDoubleOrNull() == null) {
                    isWeightErrorVisible = true
                }
            } catch (e: Exception) {
                isWeightErrorVisible = true
            }
        }
    )
}
