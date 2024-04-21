package com.example.fitjournal.core.util.localdate

import com.example.fitjournal.core.util.constants.Constants.STANDARD_DATE_PATTERN
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

// common date will mean MMM dd, yyyy
// as displayed on home screen top app bar
fun LocalDate.formatToCommonDate(): String {
    return try {
        if (this.toString().isEmpty() || this.toString() == "null") return ""
        val dateFormat = DateTimeFormatter.ofPattern(STANDARD_DATE_PATTERN)
        this.format(dateFormat)
    } catch (e: IllegalArgumentException) {
        ""
    } catch (e: DateTimeParseException) {
        ""
    } catch (e: Exception) {
        ""
    }
}
