package com.alexafit.fitjournal.home.presentation.util.constants

import com.alexafit.fitjournal.core.util.localdate.formatToCommonDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

object GeneralConstants {
    val todayDate: String = LocalDate.now().formatToCommonDate()
    val todayDateTime: LocalDateTime = LocalDateTime.now()
    val todayDateTimeInMilli: Long = (todayDateTime.toEpochSecond(ZoneOffset.UTC) * 1000)
}
