package com.example.fitjournal.core.data.model.realmdb

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class RealmWorkouts : RealmObject {
    @PrimaryKey
    var workoutId: ObjectId = BsonObjectId()
    var workouts: RealmList<RealmWorkoutEntry> = realmListOf()
}
