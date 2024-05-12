package com.example.fitjournal.home.presentation.model.ui

import com.example.fitjournal.core.domain.model.TimeModel
import com.example.fitjournal.home.presentation.model.enum.WeightLiftingWeightType

data class CalisthenicsUi(
    val reps: Int? = null,
    val sets: Int? = null,
    val weight: Double? = null,
    val weightInKgs: Double = 0.0,
    val weightType: WeightLiftingWeightType? = null,
    val time: TimeModel? = null,
    val name: String,
    val icon: Int
) {
    fun doesRepsAndSetsExist(): Boolean {
        return reps != null && sets != null
    }
}
