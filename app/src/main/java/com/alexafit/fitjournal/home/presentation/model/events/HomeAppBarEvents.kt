package com.alexafit.fitjournal.home.presentation.model.events

sealed class HomeAppBarEvents {
    data object GetPreviousDate : HomeAppBarEvents()
    data object GetNextDate : HomeAppBarEvents()
    data class ShowDatePickerDialog(val showDialog: Boolean = true) : HomeAppBarEvents()
    data class ShowHelpDialog(val showDialog: Boolean = true) : HomeAppBarEvents()
}
