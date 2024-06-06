package com.example.fitjournal.core.util.extensions

fun Double.roundToTwoDecimalPlaces() = "%.2f".format(this).toDouble() // RoundToTwoDecimalPlaces
fun Double.isInteger() = this % 1.0 == 0.0 // determine if remainder is present
