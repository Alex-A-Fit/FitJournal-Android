package com.example.fitjournal.home.presentation.mapper

import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.core.presentation.model.WorkoutDetailsUiModel
import com.example.fitjournal.core.util.extensions.convertMinutesToHours
import com.example.fitjournal.core.util.extensions.convertSecondsToMinutes
import com.example.fitjournal.core.util.extensions.roundToTwoDecimalPlaces
import com.example.fitjournal.core.util.extensions.toIntOrZero
import com.example.fitjournal.home.presentation.model.ui.CalisthenicsUi
import com.example.fitjournal.home.presentation.model.ui.CardioUi
import com.example.fitjournal.home.presentation.model.ui.WeightLiftingUi

fun WorkoutDetailsUiModel.mapToWeightLiftingUi(): WeightLiftingUi {
    if (this.exerciseCardModel == null) return WeightLiftingUi(name = this.name, icon = this.icon)
    val reps = this.exerciseCardModel.reps
    val sets = this.exerciseCardModel.sets
    val weight = this.exerciseCardModel.weight
    if (reps == null || sets == null || weight == null) return WeightLiftingUi(name = this.name, icon = this.icon)
    return WeightLiftingUi(
        reps = reps,
        sets = sets,
        weight = weight,
        weightInKgs = weight.times(0.453592).roundToTwoDecimalPlaces(),
        name = this.name,
        icon = this.icon
    )
}

fun WorkoutDetailsUiModel.mapToCalisthenicsUi(): CalisthenicsUi {
    if (this.exerciseCardModel == null) return CalisthenicsUi(name = this.name, icon = this.icon)
    val reps = this.exerciseCardModel.reps
    val sets = this.exerciseCardModel.sets
    val weight = this.exerciseCardModel.weight
    val time = reduceTimeValues(this.exerciseCardModel.time)
    if (reps == null || sets == null) return CalisthenicsUi(name = this.name, icon = this.icon)
    return CalisthenicsUi(
        reps = reps,
        sets = sets,
        weight = weight,
        weightInKgs = weight?.times(0.453592)?.roundToTwoDecimalPlaces() ?: 0.0,
        time = time,
        name = this.name,
        icon = this.icon
    )
}

fun WorkoutDetailsUiModel.mapToCardioUi(): CardioUi {
    if (this.exerciseCardModel == null) return CardioUi(name = this.name, icon = this.icon)
    val time = reduceTimeValues(this.exerciseCardModel.time)
    val distance = this.exerciseCardModel.distance
    val laps = this.exerciseCardModel.laps
    if (time == null || distance == null) return CardioUi(name = this.name, icon = this.icon)
    return CardioUi(
        time = time,
        name = this.name,
        icon = this.icon,
        distance = distance,
        distanceInKm = distance.times(1.609344).roundToTwoDecimalPlaces(),
        laps = laps
    )
}

fun reduceTimeValues(time: TimeModel?): TimeModel? {
    if (time == null) return null
    // grab total seconds and reduce to how many minutes there are if > 60
    val currentSeconds = time.seconds
    val convertedSeconds = currentSeconds.convertSecondsToMinutes()
    val seconds = convertedSeconds.second.value

    // grab total minutes and reduce to how many hours there are if > 60
    val currentMinutes = time.minutes.toIntOrZero() + convertedSeconds.first.value
    val convertedMinutes = currentMinutes.toString().convertMinutesToHours()
    val minutes = convertedMinutes.second.value

    val hours = time.hours.toIntOrZero() + convertedMinutes.first.value

    return TimeModel(
        hours = hours.toString(),
        minutes = minutes.toString(),
        seconds = seconds.toString()
    )
}
