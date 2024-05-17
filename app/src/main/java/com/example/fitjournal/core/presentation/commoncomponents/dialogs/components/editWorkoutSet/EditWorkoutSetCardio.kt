package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutDistanceSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutMandatoryTimeSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutOptionalLapsSection
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model.EditWorkoutSetCardioModel
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType

@Composable
fun EditWorkoutSetCardio(
    editWorkoutSetCardioModel: EditWorkoutSetCardioModel
) {
    var lapValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.laps)
    }
    var distanceValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.distance)
    }
    var distanceType by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.distanceType)
    }
    val hourValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.hr)
    }
    val minuteValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.min)
    }
    val secondValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.sec)
    }
    EditWorkoutOptionalLapsSection(
        isLapsErrorVisible = editWorkoutSetCardioModel.isLapErrorVisible,
        lapsValue = lapValue,
        onLapsValueChange = {
            lapValue = it
        },
        editLapsEvent = { editWorkoutFunction: EditWorkoutFunction, laps: String ->
            editWorkoutSetCardioModel.editLaps(
                editWorkoutFunction,
                laps
            )
        }
    )
    EditWorkoutDistanceSection(
        isDistanceErrorVisible = editWorkoutSetCardioModel.isDistanceErrorVisible,
        distanceType = distanceType,
        distanceValue = distanceValue,
        editDistanceTypeEvent = {
            distanceType = if (distanceType == CardioDistanceType.MILES) {
                CardioDistanceType.KILOMETERS
            } else {
                CardioDistanceType.MILES
            }
        },
        onDistanceValueChange = {
            distanceValue = it
        },
        editDistanceEvent = { editWorkoutFunction: EditWorkoutFunction, distance: String ->
            editWorkoutSetCardioModel.editDistance(
                editWorkoutFunction,
                distance
            )
        }
    )
    EditWorkoutMandatoryTimeSection(
        isTimeErrorVisible = editWorkoutSetCardioModel.isTimeErrorVisible,
        hourValue = hourValue,
        minuteValue = minuteValue,
        secondValue = secondValue,
        onTimeValueChanged = { newValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            editWorkoutSetCardioModel.editTime(
                newValue,
                editWorkoutTimeDeterminate
            )
        }
    )
}
