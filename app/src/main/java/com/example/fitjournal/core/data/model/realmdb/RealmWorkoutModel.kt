package com.example.fitjournal.core.data.model.realmdb

import io.realm.kotlin.types.EmbeddedRealmObject

class RealmWorkoutModel : EmbeddedRealmObject {
    var name: String = ""
    var type: String = ""
    var realmWorkoutProperties: RealmWorkoutProperties? = null
}
