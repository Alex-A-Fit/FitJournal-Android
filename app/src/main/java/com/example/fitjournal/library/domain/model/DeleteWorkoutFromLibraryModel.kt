package com.example.fitjournal.library.domain.model

data class DeleteWorkoutFromLibraryModel(
    val workoutName: String,
    val snackBarMessageId: Int
)
