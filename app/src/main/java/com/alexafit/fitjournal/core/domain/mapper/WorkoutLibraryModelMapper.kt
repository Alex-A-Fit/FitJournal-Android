package com.alexafit.fitjournal.core.domain.mapper

import com.alexafit.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.alexafit.fitjournal.core.data.util.getWorkoutType
import com.alexafit.fitjournal.core.domain.model.WorkoutLibraryModel

fun RealmWorkoutLibrary.mapToWorkoutLibraryModel(): WorkoutLibraryModel {
    return WorkoutLibraryModel(
        name = name,
        workoutType = type,
        workoutTypeEnum = getWorkoutType(workoutType = type)
    )
}
