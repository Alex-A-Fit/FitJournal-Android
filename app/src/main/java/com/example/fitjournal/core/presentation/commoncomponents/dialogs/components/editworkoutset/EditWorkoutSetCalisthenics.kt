package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalTimeSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalWeightSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.EditWorkoutSetCalisthenicsModel
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.util.extensions.toDoubleOrZero
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

@Composable
fun EditWorkoutSetCalisthenics(
    editWorkoutSetCalisthenicsModel: EditWorkoutSetCalisthenicsModel,
    isWorkoutValid: (Boolean, WorkoutTypeDialog?) -> Unit
) {
    var repValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.reps)
    }
    var isRepErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var setValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.sets)
    }
    var isSetErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var weightValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.weight)
    }
    var isWeightErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var weightType by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.weightType)
    }
    var hourValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.hr)
    }
    var minuteValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.min)
    }
    var secondValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.sec)
    }
    var isTimeErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = repValue, key2 = setValue, key3 = weightValue) {
        if (isRepErrorVisible || isSetErrorVisible || isTimeErrorVisible || isWeightErrorVisible) {
            isWorkoutValid(false, null)
        } else {
            isWorkoutValid(
                true,
                WorkoutTypeDialog.Calisthenics(
                    editWorkoutSetCalisthenicsModel = EditWorkoutSetCalisthenicsModel(
                        reps = repValue,
                        sets = setValue,
                        weight = weightValue,
                        weightType = weightType,
                        hr = if (hourValue.length == 1) "0$hourValue" else hourValue,
                        min = if (minuteValue.length == 1) "0$minuteValue" else minuteValue,
                        sec = if (secondValue.length == 1) "0$secondValue" else secondValue,
                        index = editWorkoutSetCalisthenicsModel.index
                    )
                )
            )
        }
    }
    LaunchedEffect(
        key1 = hourValue,
        key2 = minuteValue,
        key3 = secondValue
    ) {
        if (isRepErrorVisible || isSetErrorVisible || isTimeErrorVisible || isWeightErrorVisible) {
            isWorkoutValid(false, null)
        } else {
            isWorkoutValid(
                true,
                WorkoutTypeDialog.Calisthenics(
                    editWorkoutSetCalisthenicsModel = EditWorkoutSetCalisthenicsModel(
                        reps = repValue,
                        sets = setValue,
                        weight = weightValue,
                        weightType = weightType,
                        hr = if (hourValue.length == 1) "0$hourValue" else hourValue,
                        min = if (minuteValue.length == 1) "0$minuteValue" else minuteValue,
                        sec = if (secondValue.length == 1) "0$secondValue" else secondValue,
                        index = editWorkoutSetCalisthenicsModel.index
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
    EditWorkoutOptionalWeightSection(
        isWeightErrorVisible = isWeightErrorVisible,
        poundsOrKilogramsText = weightType.stringValue,
        weightValue = weightValue,
        onWeightTypeClicked = {
            weightType =
                if (weightType == WeightLiftingWeightType.POUNDS) {
                    WeightLiftingWeightType.KILOGRAMS
                } else {
                    WeightLiftingWeightType.POUNDS
                }
        },
        onWeightValueChange = {
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
                                EditWorkoutFunction.SUBTRACT_VALUE -> (weight.toDoubleOrZero() - 1).toString()
                            }
                    }
                }
            } catch (e: Exception) {
                isWeightErrorVisible = true
            }
        }
    )
    EditWorkoutOptionalTimeSection(
        isTimeErrorVisible = isTimeErrorVisible,
        hourValue = hourValue,
        minuteValue = minuteValue,
        secondValue = secondValue,
        onTimeValueChanged = { timeValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            isTimeErrorVisible = false
            when (editWorkoutTimeDeterminate) {
                EditWorkoutTimeDeterminate.HOUR -> {
                    hourValue = timeValue
                }

                EditWorkoutTimeDeterminate.MINUTE -> {
                    minuteValue = timeValue
                }

                EditWorkoutTimeDeterminate.SECOND -> {
                    secondValue = timeValue
                }
            }
            if (timeValue.isEmpty()) return@EditWorkoutOptionalTimeSection
            if (timeValue.toIntOrNull() == null) {
                isTimeErrorVisible = true
            }
        }
    )
}
