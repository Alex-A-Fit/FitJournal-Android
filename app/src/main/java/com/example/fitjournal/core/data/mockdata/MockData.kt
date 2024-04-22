package com.example.fitjournal.core.data.mockdata

import com.example.fitjournal.core.data.mockdata.helper.CreateMockDataSample
import com.example.fitjournal.core.data.model.realmdb.CalisthenicsSet
import com.example.fitjournal.core.data.model.realmdb.CardioSet
import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.data.model.realmdb.StrengthTrainingSet
import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import org.mongodb.kbson.ObjectId

// mock data variables that are generic functions
// are meant so we can create copies with different Ids
object MockData {
    val weightTraining1 = fun(objectId: ObjectId): RealmWorkoutEntry {
        return CreateMockDataSample.weightTrainingSample(
            workoutName = "Bench Press",
            workoutType = "Weight Training",
            workoutSet = arrayOf(
                StrengthTrainingSet().apply {
                    reps = 10
                    sets = 2
                    weight = 365.0
                }
            ),
            objectId = objectId
        )
    }

    val weightTraining2 = fun(objectId: ObjectId): RealmWorkoutEntry {
        return CreateMockDataSample.weightTrainingSample(
            workoutName = "Squats",
            workoutType = "Weight Training",
            workoutSet = arrayOf(
                StrengthTrainingSet().apply {
                    reps = 8
                    sets = 3
                    weight = 220.0
                },
                StrengthTrainingSet().apply {
                    reps = 5
                    sets = 1
                    weight = 110.0
                }
            ),
            objectId = objectId
        )
    }

    val calisthenics1 = fun(objectId: ObjectId): RealmWorkoutEntry {
        return CreateMockDataSample.calisthenicsSample(
            workoutName = "Push Ups",
            workoutType = "Calisthenics",
            workoutSet = arrayOf(
                CalisthenicsSet().apply {
                    reps = 25
                    sets = 2
                    weight = 10.0
                    time = "00:03:00"
                }
            ),
            objectId = objectId
        )
    }

    val calisthenics2 = fun(objectId: ObjectId): RealmWorkoutEntry {
        return CreateMockDataSample.calisthenicsSample(
            workoutName = "Mountain Climbers",
            workoutType = "Calisthenics",
            workoutSet = arrayOf(
                CalisthenicsSet().apply {
                    reps = 12
                    sets = 2
                },
                CalisthenicsSet().apply {
                    reps = 6
                    sets = 2
                }
            ),
            objectId = objectId
        )
    }

    val calisthenics3 = fun(objectId: ObjectId): RealmWorkoutEntry {
        return CreateMockDataSample.calisthenicsSample(
            workoutName = "Medicine Ball Slams",
            workoutType = "Calisthenics",
            workoutSet = arrayOf(
                CalisthenicsSet().apply {
                    reps = 10
                    sets = 4
                }
            ),
            objectId = objectId
        )
    }

    val cardio1 = fun(objectId: ObjectId): RealmWorkoutEntry {
        return CreateMockDataSample.cardioSample(
            workoutName = "Running",
            workoutType = "Cardio",
            workoutSet = arrayOf(
                CardioSet().apply {
                    distance = 3.2
                    time = "00:30:00"
                }
            ),
            objectId = objectId
        )
    }

    val cardio2 = fun(objectId: ObjectId): RealmWorkoutEntry {
        return CreateMockDataSample.cardioSample(
            workoutName = "Biking",
            workoutType = "Cardio",
            workoutSet = arrayOf(
                CardioSet().apply {
                    distance = 3.2
                    distanceType = CardioDistanceType.KILOMETERS.stringValue
                    time = "00:30:00"
                    laps = 13.0
                }
            ),
            objectId = objectId
        )
    }

    val libraryWorkoutList = listOf(
        "Bench",
        "Squats",
        "Lateral Raises",
        "Elevated Goblet Squats",
        "Burpees",
        "Bicep Curls",
        "Box Jumps",
        "Mountain Climbers",
        "Medicine Ball Slams",
        "Mountain Pose",
        "Modified Push-Ups",
        "Mason Twist",
        "Monkey Bars",
        "Modified Burpees",
        "Lunges",
        "Leg Press",
        "Lat Pulldowns",
        "Lying leg curls",
        "L-sit",
        "LandMine Twists"
    )
}
