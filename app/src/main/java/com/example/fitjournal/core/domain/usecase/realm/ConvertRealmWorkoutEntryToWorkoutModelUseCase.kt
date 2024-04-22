package com.example.fitjournal.core.domain.usecase.realm

import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.data.util.getWorkoutIcon
import com.example.fitjournal.core.data.util.getWorkoutType
import com.example.fitjournal.core.domain.mapper.mapRealmWorkoutPropsToWorkoutPropsModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import javax.inject.Inject

class ConvertRealmWorkoutEntryToWorkoutModelUseCase @Inject constructor() {
    operator fun invoke(databaseEntry: List<RealmWorkoutEntry>): List<WorkoutModel> {
        val databaseWorkouts = databaseEntry.map { realmWorkout ->
            val workout = realmWorkout.workout
            val workoutType = getWorkoutType(workout?.type)
            val workoutProps = workout?.realmWorkoutProperties
            WorkoutModel(
                id = realmWorkout.workoutId,
                name = workout?.name.orEmpty(),
                icon = getWorkoutIcon(workout?.type),
                workoutTypeEnum = workoutType,
                date = realmWorkout.timeStamp,
                workoutPropertiesModel = if (workoutProps == null) {
                    null
                } else {
                    mapRealmWorkoutPropsToWorkoutPropsModel(
                        workoutProps = workoutProps,
                        workoutType = workoutType
                    )
                }
            )
        }
        return filterNullOrMissingInfoWorkouts(databaseWorkouts)
    }

    private fun filterNullOrMissingInfoWorkouts(workouts: List<WorkoutModel>): List<WorkoutModel> {
        return workouts.filterNot {
            it.name.isEmpty() || it.workoutPropertiesModel == null
        }
    }
}
