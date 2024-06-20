package com.alexafit.fitjournal.home.presentation.mapper

import com.alexafit.fitjournal.core.data.model.realmdb.workout.RealmWorkout
import com.alexafit.fitjournal.core.data.model.realmdb.workout.RealmWorkoutEntry
import com.alexafit.fitjournal.core.domain.mapper.mapWorkoutPropsToRealmWorkoutProps
import com.alexafit.fitjournal.core.domain.model.WorkoutModel

fun WorkoutModel.toRealmWorkoutEntry(workoutType: String): RealmWorkoutEntry {
    val workoutModel = this
    return RealmWorkoutEntry().apply {
        this.workoutId = workoutModel.id
        this.workout = RealmWorkout().apply {
            name = workoutModel.workoutDetailsModel.name
            type = workoutType
            realmWorkoutProperties = mapWorkoutPropsToRealmWorkoutProps(
                workoutProps = workoutModel.workoutDetailsModel.workoutPropertiesModel,
                workoutType = workoutModel.workoutDetailsModel.workoutTypeEnum
            )
        }
        this.timeStamp = workoutModel.date
    }
}
