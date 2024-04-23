package com.example.fitjournal.home.presentation.util.constants

import com.example.fitjournal.core.util.localdate.formatToCommonDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

object GeneralConstants {
    val todayDate: String = LocalDate.now().formatToCommonDate()
    val todayDateTime: LocalDateTime = LocalDateTime.now()
    val todayDateTimeInMilli: Long = (todayDateTime.toEpochSecond(ZoneOffset.UTC) * 1000)
}
