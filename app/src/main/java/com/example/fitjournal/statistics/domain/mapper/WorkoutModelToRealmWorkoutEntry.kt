package com.example.fitjournal.statistics.domain.mapper

import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutModel
import com.example.fitjournal.core.domain.mapper.mapWorkoutPropsToRealmWorkoutProps
import com.example.fitjournal.core.domain.model.WorkoutModel

fun WorkoutModel.toRealmWorkoutEntry(workoutType: String): RealmWorkoutEntry {
    val workoutModel = this
    return RealmWorkoutEntry().apply {
        this.workoutId = workoutModel.id
        this.workout = RealmWorkoutModel().apply {
            name = workoutModel.name
            type = workoutType
            realmWorkoutProperties = mapWorkoutPropsToRealmWorkoutProps(
                workoutProps = workoutModel.workoutPropertiesModel,
                workoutType = workoutModel.workoutTypeEnum
            )
        }
        this.timeStamp = workoutModel.date
    }
}
