package com.alexafit.fitjournal.core.data.model.realmdb.workout

import io.realm.kotlin.types.EmbeddedRealmObject

class RealmWorkout : EmbeddedRealmObject {
    var name: String = ""
    var type: String = ""
    var realmWorkoutProperties: RealmWorkoutProperties? = null
}
