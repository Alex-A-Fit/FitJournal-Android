package com.example.fitjournal.core.data.model.realmdb

import com.example.fitjournal.home.presentation.model.enum.CardioDistanceType
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList

class RealmWorkoutProperties : EmbeddedRealmObject {
    var listOfWeightLiftingSets: RealmList<StrengthTrainingSet> = realmListOf()
    var listOfCardioSets: RealmList<CardioSet> = realmListOf()
    var listOfCalisthenicsSet: RealmList<CalisthenicsSet> = realmListOf()
}

// given a specific strength workout,
// user would have a list of reps and sets
// they would want to input along with weight.
class StrengthTrainingSet : EmbeddedRealmObject {
    var reps: Int = 0
    var sets: Int = 0
    var weight: Double = 0.0
}

class CalisthenicsSet : EmbeddedRealmObject {
    var reps: Int = 0
    var sets: Int = 0

    // time may be optional
    var time: String? = null

    // weight may be optional
    var weight: Double? = null
}

class CardioSet : EmbeddedRealmObject {
    var distance: Double = 0.0
    var distanceType: String = CardioDistanceType.MILES.stringValue
    var time: String = ""

    // laps may be optional
    var laps: Double? = null
}
