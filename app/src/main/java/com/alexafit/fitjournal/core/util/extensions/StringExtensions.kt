package com.alexafit.fitjournal.core.util.extensions

import com.alexafit.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate

fun String.toDoubleOrZero(): Double {
    return if (this.isBlank()) 0.0 else this.toDoubleOrNull() ?: 0.0
}

fun String.toIntOrZero(): Int {
    return if (this.isBlank()) 0 else this.toIntOrNull() ?: 0
}

fun String.TwoDecimalOrNoDecimal(): String {
    return if (this.contains(".")) {
        val decimalValue = this.substringAfter(".")
        return if (decimalValue.length == 1) {
            if (decimalValue[0] == '0') {
                this.substringBefore(".")
            } else {
                this + "0"
            }
        } else {
            this
        }
    } else {
        this
    }
}

fun String?.getTimeForUi(timeDeterminate: EditWorkoutTimeDeterminate): String {
    if (this.isNullOrEmpty()) return ""
    return when (val time = this.toIntOrZero()) {
        0 -> ""
        1 -> {
            when (timeDeterminate) {
                EditWorkoutTimeDeterminate.HOUR -> "1 Hour "
                EditWorkoutTimeDeterminate.MINUTE -> "1 Minute "
                EditWorkoutTimeDeterminate.SECOND -> "1 Second "
            }
        }

        else -> {
            when (timeDeterminate) {
                EditWorkoutTimeDeterminate.HOUR -> "$time Hours "
                EditWorkoutTimeDeterminate.MINUTE -> "$time Minutes "
                EditWorkoutTimeDeterminate.SECOND -> "$time Seconds "
            }
        }
    }
}

fun String.convertMinutesToHours(): Pair<Hours, Minutes> {
    var minutes = this.toIntOrZero()
    if (minutes <= 59) return Pair(Hours(0), Minutes(minutes))
    var hours = 0
    while (minutes >= 60) {
        minutes -= 60
        hours++
    }
    return Pair(Hours(hours), Minutes(minutes))
}

fun String.convertSecondsToMinutes(): Pair<Minutes, Seconds> {
    var seconds = this.toIntOrZero()
    if (seconds <= 59) return Pair(Minutes(0), Seconds(seconds))

    var minutes = 0
    while (seconds >= 60) {
        seconds -= 60
        minutes++
    }
    return Pair(Minutes(minutes), Seconds(seconds))
}

data class Hours(
    val value: Int
)

data class Minutes(
    val value: Int
)

data class Seconds(
    val value: Int
)
