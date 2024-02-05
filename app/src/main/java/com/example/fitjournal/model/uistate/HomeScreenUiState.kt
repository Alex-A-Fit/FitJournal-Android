package com.example.fitjournal.model.uistate

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

data class HomeScreenUiState(
    val currentDateTime: LocalDateTime = LocalDateTime.now(),
    val currentDate: String = currentDateTime.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.US)),
    val currentDateInMillis: Long = (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) * 1000),
    val isDatePickerDialogShowing: Boolean = false
)
