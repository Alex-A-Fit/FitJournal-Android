package com.alexafit.fitjournal.home.presentation.model.events

sealed class HomeScreenEvents {
    data object DismissDatePicker : HomeScreenEvents()
    data object ClearFilterExercisesDialog : HomeScreenEvents()
    data object SyncRealmWorkoutEntryFromDb : HomeScreenEvents()
    data class SelectDateFromDatePicker(val userSelectedDate: Long) : HomeScreenEvents()
    data class UpdateHelpDialog(
        val isDialogShowing: Boolean,
        val onCallback: () -> Unit
    ) : HomeScreenEvents()
}
