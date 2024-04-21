package com.example.fitjournal.core.domain.usecase.realm

import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.data.util.getWorkoutIcon
import com.example.fitjournal.core.data.util.getWorkoutProperties
import com.example.fitjournal.core.data.util.getWorkoutType
import com.example.fitjournal.core.domain.model.WorkoutModel
import javax.inject.Inject

class ConvertDatabaseRealmWorkoutEntryToUiUseCase @Inject constructor() {
    operator fun invoke(databaseEntry: List<RealmWorkoutEntry>): List<WorkoutModel> {
        val databaseWorkouts = databaseEntry.map { realmWorkout ->
            val workout = realmWorkout.workout
            val workoutType = getWorkoutType(workout?.type)
            val workoutProps = workout?.workoutProperties
            WorkoutModel(
                name = workout?.name.orEmpty(),
                icon = getWorkoutIcon(workout?.type),
                workoutTypeEnum = workoutType,
                date = realmWorkout.timeStamp,
                workoutPropertiesModel = if (workoutProps == null) {
                    null
                } else {
                    getWorkoutProperties(
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
