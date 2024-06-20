package com.alexafit.fitjournal.home.presentation.model.enum

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

    fun getOtherWeightType(): WeightLiftingWeightType {
        return if (this.stringValue == POUNDS.stringValue) {
            KILOGRAMS
        } else {
            POUNDS
        }
    }
}
