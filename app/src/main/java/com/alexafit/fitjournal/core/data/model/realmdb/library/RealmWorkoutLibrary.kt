package com.alexafit.fitjournal.core.data.model.realmdb.library

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RealmWorkoutLibrary : RealmObject {
    @PrimaryKey
    var id: String = ObjectId().toHexString()
    var name: String = ""
    var type: String = ""
}
