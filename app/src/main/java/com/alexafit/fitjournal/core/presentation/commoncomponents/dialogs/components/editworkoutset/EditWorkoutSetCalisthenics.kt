package com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalTimeSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalWeightSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.EditWorkoutSetCalisthenicsModel
import com.alexafit.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.alexafit.fitjournal.core.presentation.utils.EditWorkoutSetUtilFunctions
import com.alexafit.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

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
            val (showError, value) = EditWorkoutSetUtilFunctions.editRepsOrSets(
                editWorkoutFunction = editWorkoutFunction,
                repsOrSetsValue = sets
            )
            isSetErrorVisible = showError
            setValue = value
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
            val (showError, value) = EditWorkoutSetUtilFunctions.editRepsOrSets(
                editWorkoutFunction = editWorkoutFunction,
                repsOrSetsValue = reps
            )
            isRepErrorVisible = showError
            repValue = value
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
            val (showError, value) = EditWorkoutSetUtilFunctions.editWeightOrDistance(
                editWorkoutFunction = editWorkoutFunction,
                value = weight,
                isValueOptional = true
            )
            isWeightErrorVisible = showError
            weightValue = value
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
