package com.example.fitjournal.managers

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateManager {
    private val dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    fun getNextDate(
        currentDate: String
    ): String {
        val date = LocalDate.parse(currentDate, dateFormat)
        val newDateString = date.plusDays(/* daysToAdd = */ 1).format(
            DateTimeFormatter.ISO_DATE.withLocale(
                Locale.US
            )
        )
        return formatDate(newDateString)
    }

    fun getPreviousDate(
        currentDate: String
    ): String {
        val date = LocalDate.parse(currentDate, dateFormat)
        val newDateString = date.minusDays(/* daysToSubtract = */ 1).format(
            DateTimeFormatter.ISO_DATE.withLocale(
                Locale.US
            )
        )
        return formatDate(newDateString)
    }

    fun formatDate(dateString: String): String{
        return LocalDate.parse(dateString).format(dateFormat).toString()
    }
}