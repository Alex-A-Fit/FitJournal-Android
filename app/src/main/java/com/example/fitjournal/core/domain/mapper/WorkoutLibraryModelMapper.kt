package com.example.fitjournal.core.domain.mapper

import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.core.data.util.getWorkoutType
import com.example.fitjournal.core.domain.model.WorkoutLibraryModel

fun RealmWorkoutLibrary.mapToWorkoutLibraryModel(): WorkoutLibraryModel {
    return WorkoutLibraryModel(
        name = name,
        workoutType = type,
        workoutTypeEnum = getWorkoutType(workoutType = type)
    )
}
