package com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutDistanceSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutMandatoryTimeSection
import com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout.cardio.components.EditWorkoutOptionalLapsSection
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.EditWorkoutSetCardioModel
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editworkoutset.model.WorkoutTypeDialog
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutFunction
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
import com.example.fitjournal.core.util.extensions.roundToTwoDecimalPlaces
import com.example.fitjournal.core.util.extensions.toDoubleOrZero
import com.example.fitjournal.core.util.extensions.toIntOrZero
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType

@Composable
fun EditWorkoutSetCardio(
    editWorkoutSetCardioModel: EditWorkoutSetCardioModel,
    isWorkoutValid: (Boolean, WorkoutTypeDialog?) -> Unit
) {
    var lapValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.laps)
    }
    var isLapErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var distanceValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.distance)
    }
    var isDistanceErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var distanceType by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.distanceType)
    }
    var hourValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.hr)
    }
    var minuteValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.min)
    }
    var secondValue by rememberSaveable {
        mutableStateOf(editWorkoutSetCardioModel.sec)
    }
    var isTimeErrorVisible by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(
        key1 = lapValue,
        key2 = distanceValue,
        key3 = distanceType
    ) {
        if (isTimeErrorVisible || isLapErrorVisible || isDistanceErrorVisible) {
            isWorkoutValid(false, null)
        } else {
            isWorkoutValid(
                true,
                WorkoutTypeDialog.Cardio(
                    editWorkoutSetCardioModel = EditWorkoutSetCardioModel(
                        laps = lapValue,
                        distance = distanceValue,
                        distanceType = distanceType,
                        hr = if (hourValue.length == 1) "0$hourValue" else hourValue,
                        min = if (minuteValue.length == 1) "0$minuteValue" else minuteValue,
                        sec = if (secondValue.length == 1) "0$secondValue" else secondValue,
                        index = editWorkoutSetCardioModel.index
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
        if (isTimeErrorVisible || isLapErrorVisible || isDistanceErrorVisible) {
            isWorkoutValid(false, null)
        } else {
            isWorkoutValid(
                true,
                WorkoutTypeDialog.Cardio(
                    editWorkoutSetCardioModel = EditWorkoutSetCardioModel(
                        laps = lapValue,
                        distance = distanceValue,
                        distanceType = distanceType,
                        hr = if (hourValue.length == 1) "0$hourValue" else hourValue,
                        min = if (minuteValue.length == 1) "0$minuteValue" else minuteValue,
                        sec = if (secondValue.length == 1) "0$secondValue" else secondValue,
                        index = editWorkoutSetCardioModel.index
                    )
                )
            )
        }
    }
    EditWorkoutOptionalLapsSection(
        isLapsErrorVisible = isLapErrorVisible,
        lapsValue = lapValue,
        onLapsValueChange = {
            isLapErrorVisible = it == "0"
            lapValue = it
        },
        editLapsEvent = { editWorkoutFunction: EditWorkoutFunction, laps: String ->
            isLapErrorVisible = false
            try {
                if (laps != "0" && laps.isNotEmpty()) {
                    var lapsDouble = laps.toDoubleOrNull()
                    if (lapsDouble == null) {
                        isLapErrorVisible = true
                        return@EditWorkoutOptionalLapsSection
                    }
                    when (editWorkoutFunction) {
                        EditWorkoutFunction.ADD_VALUE -> lapsDouble += 1.0
                        EditWorkoutFunction.SUBTRACT_VALUE -> lapsDouble -= 1.0
                    }
                    lapValue =
                        if (lapsDouble < 0) "0" else lapsDouble.roundToTwoDecimalPlaces().toString()
                } else if (laps == "0" || laps.isEmpty()) {
                    lapValue = when (editWorkoutFunction) {
                        EditWorkoutFunction.ADD_VALUE -> "1"
                        EditWorkoutFunction.SUBTRACT_VALUE -> "0"
                    }
                }
                if (lapValue == "0") {
                    isLapErrorVisible = true
                }
            } catch (e: Exception) {
                isLapErrorVisible = true
            }
        }
    )
    EditWorkoutDistanceSection(
        isDistanceErrorVisible = isDistanceErrorVisible,
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
            isDistanceErrorVisible = false
            try {
                when {
                    distance.isEmpty() || distance.toDoubleOrZero() == 0.0 -> {
                        distanceValue = when (editWorkoutFunction) {
                            EditWorkoutFunction.ADD_VALUE -> "1.0"
                            EditWorkoutFunction.SUBTRACT_VALUE -> "0"
                        }
                    }

                    distance.last() == '.' -> {
                        val oldWeight = distance.substringBefore(".")
                        distanceValue = try {
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
                        distanceValue =
                            when (editWorkoutFunction) {
                                EditWorkoutFunction.ADD_VALUE -> (distance.toDoubleOrZero() + 1).toString()
                                EditWorkoutFunction.SUBTRACT_VALUE -> {
                                    val newDistance = distance.toDoubleOrZero() - 1
                                    when {
                                        newDistance <= 0 -> "0"
                                        else -> newDistance.roundToTwoDecimalPlaces().toString()
                                    }
                                }
                            }
                    }
                }
                when {
                    distanceValue.toDoubleOrZero() == 0.0 -> isDistanceErrorVisible = true
                }
            } catch (e: Exception) {
                isDistanceErrorVisible = true
            }
        }
    )
    EditWorkoutMandatoryTimeSection(
        isTimeErrorVisible = isTimeErrorVisible,
        hourValue = hourValue,
        minuteValue = minuteValue,
        secondValue = secondValue,
        onTimeValueChanged = { newValue: String, editWorkoutTimeDeterminate: EditWorkoutTimeDeterminate ->
            isTimeErrorVisible = false
            when (editWorkoutTimeDeterminate) {
                EditWorkoutTimeDeterminate.HOUR -> {
                    hourValue = newValue
                }

                EditWorkoutTimeDeterminate.MINUTE -> {
                    minuteValue = newValue
                }

                EditWorkoutTimeDeterminate.SECOND -> {
                    secondValue = newValue
                }
            }
            if (hourValue.toIntOrZero() == 0 && minuteValue.toIntOrZero() == 0 && secondValue.toIntOrZero() == 0) {
                isTimeErrorVisible = true
            }
        }
    )
}
