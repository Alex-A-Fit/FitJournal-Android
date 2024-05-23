package com.example.fitjournal.core.domain.managers

import com.example.fitjournal.core.util.localdate.formatToCommonDate
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateManager {
    private val dateFormat = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    fun getNextDate(
        currentDate: LocalDateTime
    ): UserSelectedDate {
        val nextDate = currentDate.plusDays(/* days = */ 1)
        val newDateString = nextDate.format(
            DateTimeFormatter.ISO_DATE.withLocale(
                Locale.getDefault()
            )
        )
        return UserSelectedDate(
            localDateTime = nextDate,
            localDateString = formatDate(newDateString)
        )
    }

    fun getPreviousDate(
        currentDate: LocalDateTime
    ): UserSelectedDate {
        val previousDate = currentDate.minusDays(/* days = */ 1)
        val newDateString = previousDate.format(
            DateTimeFormatter.ISO_DATE.withLocale(
                Locale.getDefault()
            )
        )
        return UserSelectedDate(
            localDateTime = previousDate,
            localDateString = formatDate(newDateString)
        )
    }

    fun getSelectedDate(currentDate: Long): UserSelectedDate {
        val instant = Instant.ofEpochMilli(currentDate)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).plusDays(1)
        val localDate = localDateTime.toLocalDate()
        return UserSelectedDate(
            localDateTime = localDateTime,
            localDateString = localDate.formatToCommonDate()
        )
    }

    private fun formatDate(dateString: String): String {
        return LocalDate.parse(dateString).format(dateFormat).toString()
    }

    fun getTimeInMilliseconds(currentDate: LocalDateTime): Long {
        return currentDate.toEpochSecond(ZoneOffset.UTC) * 1000
    }
    fun getCommonDateFormat(): DateTimeFormatter {
        return dateFormat
    }
}

data class UserSelectedDate(
    val localDateTime: LocalDateTime,
    val localDateString: String
)
