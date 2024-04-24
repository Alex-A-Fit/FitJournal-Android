package com.example.fitjournal.core.data.model.realmdb.library

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RealmWorkoutLibrary : RealmObject {
    @PrimaryKey
    var name: String = ""
    var type: String = ""
}
