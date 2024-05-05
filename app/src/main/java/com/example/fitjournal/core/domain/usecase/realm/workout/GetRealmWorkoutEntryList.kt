package com.example.fitjournal.core.domain.usecase.realm.workout

import com.example.fitjournal.core.data.model.realmdb.workout.RealmWorkoutEntry
import com.example.fitjournal.core.data.util.getWorkoutIcon
import com.example.fitjournal.core.data.util.getWorkoutType
import com.example.fitjournal.core.domain.mapper.mapRealmWorkoutPropsToWorkoutPropsModel
import com.example.fitjournal.core.domain.model.WorkoutDetailsModel
import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.model.WorkoutPropertiesModel
import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import javax.inject.Inject

class GetRealmWorkoutEntryList @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    suspend operator fun invoke(): List<WorkoutModel> {
        val realmList = realmWorkoutEntryRepository.getRealmWorkoutEntryList()
        return if (realmList.isNotEmpty()) {
            return convertRealmWorkoutEntryToWorkoutModelUseCase(realmList)
        } else {
            emptyList()
        }
    }
}

fun convertRealmWorkoutEntryToWorkoutModelUseCase(databaseEntry: List<RealmWorkoutEntry>): List<WorkoutModel> {
    val databaseWorkouts = databaseEntry.map { realmWorkout ->
        val workout = realmWorkout.workout
        val workoutType = getWorkoutType(workout?.type)
        val workoutProps = workout?.realmWorkoutProperties
        WorkoutModel(
            id = realmWorkout.workoutId,
            workoutDetailsModel = WorkoutDetailsModel(
                name = workout?.name.orEmpty(),
                icon = getWorkoutIcon(workout?.type),
                workoutTypeEnum = workoutType,
                workoutPropertiesModel = if (workoutProps != null) {
                    mapRealmWorkoutPropsToWorkoutPropsModel(
                        workoutProps = workoutProps,
                        workoutType = workoutType
                    )
                } else {
                    when (workoutType) {
                        WorkoutTypeEnum.WEIGHT_TRAINING -> {
                            WorkoutPropertiesModel.WeightLiftingProps(
                                props = emptyList()
                            )
                        }

                        WorkoutTypeEnum.CALISTHENICS -> {
                            WorkoutPropertiesModel.CalisthenicsProps(
                                props = emptyList()
                            )
                        }

                        WorkoutTypeEnum.CARDIO -> {
                            WorkoutPropertiesModel.CardioProps(
                                props = emptyList()
                            )
                        }
                    }
                }
            ),
            date = realmWorkout.timeStamp
        )
    }
    return filterNullOrMissingInfoWorkouts(databaseWorkouts)
}

private fun filterNullOrMissingInfoWorkouts(workouts: List<WorkoutModel>): List<WorkoutModel> {
    return workouts.filterNot {
        it.workoutDetailsModel.name.isEmpty()
    }
}
