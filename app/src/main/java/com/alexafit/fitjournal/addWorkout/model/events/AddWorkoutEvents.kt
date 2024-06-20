package com.alexafit.fitjournal.addWorkout.model.events

import android.content.Context
import com.alexafit.fitjournal.library.domain.model.AddWorkoutToLibraryModel

sealed class AddWorkoutEvents {
    data object ClearSearchBarFilter : AddWorkoutEvents()
    data class FilterSearchByWorkout(val workout: String) : AddWorkoutEvents()

    data class AddWorkoutToLibrary(
        val workout: AddWorkoutToLibraryModel,
        val showSnackBar: suspend (String) -> Unit,
        val context: Context
    ) : AddWorkoutEvents()

    data object SyncRealmWorkoutEntryFromDb : AddWorkoutEvents()
}
