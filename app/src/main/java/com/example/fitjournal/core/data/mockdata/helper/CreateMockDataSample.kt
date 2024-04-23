package com.example.fitjournal.core.data.mockdata.helper

import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.core.data.model.realmdb.workout.CalisthenicsSet
import com.example.fitjournal.core.data.model.realmdb.workout.CardioSet
import com.example.fitjournal.core.data.model.realmdb.workout.RealmWorkout
import com.example.fitjournal.core.data.model.realmdb.workout.RealmWorkoutEntry
import com.example.fitjournal.core.data.model.realmdb.workout.RealmWorkoutProperties
import com.example.fitjournal.core.data.model.realmdb.workout.StrengthTrainingSet
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

object CreateMockDataSample {
    fun weightTrainingSample(
        workoutName: String,
        workoutType: String,
        objectId: ObjectId,
        vararg workoutSet: StrengthTrainingSet
    ): RealmWorkoutEntry {
        return RealmWorkoutEntry().apply {
            workout = RealmWorkout().apply {
                name = workoutName
                type = workoutType
                realmWorkoutProperties = RealmWorkoutProperties().apply {
                    listOfWeightLiftingSets = workoutSet.map { it }.toRealmList()
                }
            }
            workoutId = objectId
        }
    }

    fun calisthenicsSample(
        workoutName: String,
        workoutType: String,
        objectId: ObjectId,
        vararg workoutSet: CalisthenicsSet
    ): RealmWorkoutEntry {
        return RealmWorkoutEntry().apply {
            workout = RealmWorkout().apply {
                name = workoutName
                type = workoutType
                realmWorkoutProperties = RealmWorkoutProperties().apply {
                    listOfCalisthenicsSet = workoutSet.map { it }.toRealmList()
                }
            }
            workoutId = objectId
        }
    }

    fun cardioSample(
        workoutName: String,
        workoutType: String,
        objectId: ObjectId,
        vararg workoutSet: CardioSet
    ): RealmWorkoutEntry {
        return RealmWorkoutEntry().apply {
            workout = RealmWorkout().apply {
                name = workoutName
                type = workoutType
                realmWorkoutProperties = RealmWorkoutProperties().apply {
                    listOfCardioSets = workoutSet.map { it }.toRealmList()
                }
            }
            workoutId = objectId
        }
    }

    fun libraryItemSample(
        workoutName: String,
        workoutType: String
    ): RealmWorkoutLibrary {
        return RealmWorkoutLibrary().apply {
            name = workoutName
            type = workoutType
        }
    }
}
