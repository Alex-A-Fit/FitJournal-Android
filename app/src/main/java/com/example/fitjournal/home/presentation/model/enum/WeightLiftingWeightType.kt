package com.example.fitjournal.home.presentation.model.enum

enum class WeightLiftingWeightType(
    val stringValue: String,
    val stringConcatenatedValue: String
) {

    POUNDS(
        stringValue = "pounds",
        stringConcatenatedValue = "lbs"
    ),
    KILOGRAMS(
        stringValue = "kilograms",
        stringConcatenatedValue = "kgs"
    );

    fun getNextDistanceType(): WeightLiftingWeightType {
        return if (this.stringValue == POUNDS.stringValue) {
            KILOGRAMS
        } else {
            POUNDS
        }
    }
}
