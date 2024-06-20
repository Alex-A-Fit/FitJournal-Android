package com.alexafit.fitjournal.core.data.model.realmdb.workout

import com.alexafit.fitjournal.core.util.localdate.formatToCommonDate
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDate

class RealmWorkoutEntry : RealmObject {
    @PrimaryKey
    var workoutId: String = ObjectId().toHexString()
    var workout: RealmWorkout? = null

    // time should be in MMM dd, yyyy format
    var timeStamp: String = LocalDate.now().formatToCommonDate()
}
