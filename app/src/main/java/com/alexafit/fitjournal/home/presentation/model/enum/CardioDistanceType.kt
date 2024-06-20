package com.alexafit.fitjournal.home.presentation.model.enum

enum class CardioDistanceType(
    val stringValue: String,
    val stringConcatenatedValue: String
) {

    MILES(
        stringValue = "miles",
        stringConcatenatedValue = "mi"
    ),
    KILOMETERS(
        stringValue = "kilometers",
        stringConcatenatedValue = "km"
    );

    fun getOtherDistanceType(): CardioDistanceType {
        return if (this.stringValue == MILES.stringValue) {
            KILOMETERS
        } else {
            MILES
        }
    }
}
