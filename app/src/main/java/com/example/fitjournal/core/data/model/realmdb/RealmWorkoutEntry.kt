package com.example.fitjournal.core.data.model.realmdb

import com.example.fitjournal.core.util.localdate.formatToCommonDate
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

class RealmWorkoutEntry : RealmObject {
    @PrimaryKey
    var workoutId: ObjectId = ObjectId()
    var workout: RealmWorkoutModel? = null

    // time should be in MMM dd, yyyy format
    var timeStamp: String = LocalDate.now().formatToCommonDate()
}
