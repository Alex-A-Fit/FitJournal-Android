package com.alexafit.fitjournal.library.domain.model

data class UpdateWorkoutLibraryModel(
    // original item
    val originalWorkoutName: String,
    // updated values
    val newName: String? = null,
    val newWorkoutTypeEnum: String
)
