package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.weightlifting.components.EditWorkoutMandatoryWeightSection
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model.EditWorkoutSetWeightLiftingModel
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

@Composable
fun EditWorkoutSetWeightLifting(editWorkoutSetWeightLiftingModel: EditWorkoutSetWeightLiftingModel) {
    var repValue by rememberSaveable {
        mutableStateOf(editWorkoutSetWeightLiftingModel.reps)
    }
    var setValue by rememberSaveable {
        mutableStateOf(editWorkoutSetWeightLiftingModel.sets)
    }
    var weightValue by rememberSaveable {
        mutableStateOf(editWorkoutSetWeightLiftingModel.weight)
    }
    var weightType by rememberSaveable {
        mutableStateOf(editWorkoutSetWeightLiftingModel.weightType)
    }
    EditWorkoutSetsSection(
        isErrorVisible = editWorkoutSetWeightLiftingModel.isSetsErrorVisible,
        editSetsValue = { editWorkoutFunction: EditWorkoutFunction, sets: String ->
            editWorkoutSetWeightLiftingModel.editSets(
                editWorkoutFunction,
                sets
            )
        },
        setsValue = setValue,
        onSetValueChange = {
            setValue = it
        }
    )
    EditWorkoutRepsSection(
        isRepsErrorVisible = editWorkoutSetWeightLiftingModel.isRepsErrorVisible,
        editRepValue = { editWorkoutFunction: EditWorkoutFunction, reps: String ->
            editWorkoutSetWeightLiftingModel.editReps(
                editWorkoutFunction,
                reps
            )
        },
        repValue = repValue,
        onRepValueChange = {
            repValue = it
        }
    )
    EditWorkoutMandatoryWeightSection(
        isWeightErrorVisible = editWorkoutSetWeightLiftingModel.isWeightErrorVisible,
        poundsOrKilogramsText = weightType.stringValue,
        weightValue = weightValue,
        onWeightTypeClicked = {
            weightType =
                (
                    if (weightType == WeightLiftingWeightType.POUNDS) {
                        WeightLiftingWeightType.KILOGRAMS
                    } else {
                        WeightLiftingWeightType.POUNDS
                    }
                    )
        },
        onWeightValueChange = {
            weightValue = it
        },
        editWeightValue = { editWorkoutFunction: EditWorkoutFunction, weight: String ->
            editWorkoutSetWeightLiftingModel.editWeight(
                editWorkoutFunction,
                weight
            )
        }
    )
}
