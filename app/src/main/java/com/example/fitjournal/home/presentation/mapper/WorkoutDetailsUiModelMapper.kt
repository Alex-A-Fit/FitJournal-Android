package com.example.fitjournal.home.presentation.mapper

import com.example.fitjournal.core.presentation.model.WorkoutDetailsUiModel
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
        name = this.name,
        icon = this.icon
    )
}

fun WorkoutDetailsUiModel.mapToCalisthenicsUi(): CalisthenicsUi {
    if (this.exerciseCardModel == null) return CalisthenicsUi(name = this.name, icon = this.icon)
    val reps = this.exerciseCardModel.reps
    val sets = this.exerciseCardModel.sets
    val weight = this.exerciseCardModel.weight
    val time = this.exerciseCardModel.time
    if (reps == null || sets == null) return CalisthenicsUi(name = this.name, icon = this.icon)
    return CalisthenicsUi(
        reps = reps,
        sets = sets,
        weight = weight,
        time = time,
        name = this.name,
        icon = this.icon
    )
}

fun WorkoutDetailsUiModel.mapToCardioUi(): CardioUi {
    if (this.exerciseCardModel == null) return CardioUi(name = this.name, icon = this.icon)
    val time = this.exerciseCardModel.time
    val distance = this.exerciseCardModel.distance
    val distanceType = this.exerciseCardModel.distanceType
    val laps = this.exerciseCardModel.laps
    if (time == null || distance == null) return CardioUi(name = this.name, icon = this.icon)
    return CardioUi(
        time = time,
        name = this.name,
        icon = this.icon,
        distanceType = distanceType,
        distance = distance,
        laps = laps
    )
}
