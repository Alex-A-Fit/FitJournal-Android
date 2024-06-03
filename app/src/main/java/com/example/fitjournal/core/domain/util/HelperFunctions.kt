package com.example.fitjournal.core.domain.util

import com.example.fitjournal.core.util.constants.Constants.STANDARD_DATE_PATTERN
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object HelperFunctions {
    fun parseDate(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(STANDARD_DATE_PATTERN))
    }

    fun getDateStringFromEpochDays(epochDays: Float): String {
        val epochMillis = epochDays * 24 * 60 * 60 * 1000L
        val localDate = Instant.ofEpochMilli(epochMillis.toLong()).atZone(ZoneId.systemDefault()).toLocalDate()
        return localDate.format(DateTimeFormatter.ofPattern(STANDARD_DATE_PATTERN))
    }
}
