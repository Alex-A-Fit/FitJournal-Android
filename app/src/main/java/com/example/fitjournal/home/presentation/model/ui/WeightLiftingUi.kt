package com.example.fitjournal.home.presentation.model.ui

data class WeightLiftingUi(
    val reps: Int? = null,
    val sets: Int? = null,
    val weight: Double? = null,
    val weightInKgs: Double = 0.0,
    val name: String,
    val icon: Int
) {
    fun doesWeightLiftingPropertyExist(): Boolean {
        return reps != null && sets != null && weight != null
    }
}
