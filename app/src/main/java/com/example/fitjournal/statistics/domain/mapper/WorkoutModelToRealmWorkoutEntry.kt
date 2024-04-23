package com.example.fitjournal.statistics.domain.mapper

import com.example.fitjournal.core.data.model.realmdb.RealmWorkout
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.domain.mapper.mapWorkoutPropsToRealmWorkoutProps
import com.example.fitjournal.core.domain.model.WorkoutModel

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
