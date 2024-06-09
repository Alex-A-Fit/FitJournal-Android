package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.components.EditWorkoutMandatoryWeightSection
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.EditWorkoutSetWeightLiftingModel
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.utils.EditWorkoutSetUtilFunctions
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
            val (showError, value) = EditWorkoutSetUtilFunctions.editWeightOrDistance(
                editWorkoutFunction = editWorkoutFunction,
                value = weight,
                isValueOptional = false
            )
            isWeightErrorVisible = showError
            weightValue = value
        }
    )
}
