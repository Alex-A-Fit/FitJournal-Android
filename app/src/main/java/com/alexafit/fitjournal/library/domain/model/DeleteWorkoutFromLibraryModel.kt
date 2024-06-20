package com.alexafit.fitjournal.library.domain.model

data class DeleteWorkoutFromLibraryModel(
    val workoutName: String,
    val snackBarMessageId: Int
)
