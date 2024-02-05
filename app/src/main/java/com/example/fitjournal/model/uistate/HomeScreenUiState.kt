package com.example.fitjournal.model.uistate

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class HomeScreenUiState(
    val currentDate: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.US))
)
