package com.example.fitjournal.model.events

import androidx.compose.material3.SnackbarHostState
import com.example.fitjournal.model.enum.WorkoutTypeEnum

sealed class HomeScreenEvents {
    data object DismissDatePicker: HomeScreenEvents()
    data object DismissFilterExercisesDialog: HomeScreenEvents()
    data class OnConfirmFilterExercisesDialog(val filterList: List<WorkoutTypeEnum>): HomeScreenEvents()
    data class SelectDateFromDatePicker(
        val userSelectedDate: Long,
        val snackBarHostState: SnackbarHostState
    ): HomeScreenEvents()
    data class UpdateFilterDialog(val isDialogShowing: Boolean): HomeScreenEvents()
}