package com.example.fitjournal.model.events

import androidx.compose.material3.SnackbarHostState

sealed class HomeScreenEvents {
    data class SelectDateFromDatePicker(
        val userSelectedDate: Long,
        val snackBarHostState: SnackbarHostState
    ): HomeScreenEvents()
    data object DismissDatePicker: HomeScreenEvents()
}