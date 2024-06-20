package com.alexafit.fitjournal.core.util.localdate

import com.alexafit.fitjournal.core.util.constants.Constants.STANDARD_DATE_FORMATTER
import java.time.LocalDate
import java.time.format.DateTimeParseException

// common date will mean MMM dd, yyyy
// as displayed on home screen top app bar
fun LocalDate.formatToCommonDate(): String {
    return try {
        if (this.toString().isEmpty() || this.toString() == "null") return ""
        this.format(STANDARD_DATE_FORMATTER)
    } catch (e: IllegalArgumentException) {
        ""
    } catch (e: DateTimeParseException) {
        ""
    } catch (e: Exception) {
        ""
    }
}
