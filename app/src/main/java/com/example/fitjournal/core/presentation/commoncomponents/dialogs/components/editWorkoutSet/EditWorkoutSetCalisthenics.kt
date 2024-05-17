package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalTimeSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.calisthenics.components.EditWorkoutOptionalWeightSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutRepsSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.commoncomponents.EditWorkoutSetsSection
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model.EditWorkoutSetCalisthenicsModel
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

@Composable
fun EditWorkoutSetCalisthenics(
    editWorkoutSetCalisthenicsModel: EditWorkoutSetCalisthenicsModel
) {
    var repValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.reps)
    }
    var setValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.sets)
    }
    var weightValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.weight)
    }
    var weightType by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.weightType)
    }
    val hourValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.hr)
    }
    val minuteValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.min)
    }
    val secondValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCalisthenicsModel.sec)
    }

    EditWorkoutSetsSection(
        isErrorVisible = editWorkoutSetCalisthenicsModel.isSetsErrorVisible,
        editSetsValue = { editWorkoutFunction: EditWorkoutFunction, sets: String ->
            editWorkoutSetCalisthenicsModel.editSets(
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
        isRepsErrorVisible = editWorkoutSetCalisthenicsModel.isRepsErrorVisible,
        editRepValue = { editWorkoutFunction: EditWorkoutFunction, reps: String ->
            editWorkoutSetCalisthenicsModel.editReps(
                editWorkoutFunction,
                reps
            )
        },
        repValue = repValue,
        onRepValueChange = {
            repValue = it
        }
    )
    EditWorkoutOptionalWeightSection(
        isWeightErrorVisible = editWorkoutSetCalisthenicsModel.isWeightErrorVisible,
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
            editWorkoutSetCalisthenicsModel.editWeight(
                editWorkoutFunction,
                weight
            )
        }
    )
    EditWorkoutOptionalTimeSection(
        isTimeErrorVisible = editWorkoutSetCalisthenicsModel.isTimeErrorVisible,
        hourValue = hourValue,
        minuteValue = minuteValue,
        secondValue = secondValue,
        onTimeValueChanged = { timeValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            editWorkoutSetCalisthenicsModel.editTime(
                timeValue,
                editWorkoutTimeDeterminate
            )
        }
    )
}
