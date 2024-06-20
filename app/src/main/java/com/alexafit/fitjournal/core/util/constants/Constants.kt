package com.alexafit.fitjournal.core.util.constants

import java.time.format.DateTimeFormatter

object Constants {
    const val STANDARD_DATE_PATTERN = "MMM dd, yyyy"
    val STANDARD_DATE_FORMATTER = DateTimeFormatter.ofPattern(STANDARD_DATE_PATTERN)
    const val EMPTY_SPACE = " "
    const val KILOGRAMS_TO_POUNDS_CONVERSION_FACTOR = 2.20462
    const val POUNDS_TO_KILOGRAMS_CONVERSION_FACTOR = 0.453592
    const val MILES_TO_KILOMETERS_CONVERSION_FACTOR = 1.609344
    const val KILOMETERS_TO_MILES_CONVERSION_FACTOR = 0.621371
    const val HOUR_TO_SECONDS_CONVERSION_FACTOR = 3600.0
    const val MINUTE_TO_SECONDS_CONVERSION_FACTOR = 60.0
}
