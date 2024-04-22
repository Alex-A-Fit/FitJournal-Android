package com.example.fitjournal.core.data.util

import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.types.TypedRealmObject
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

fun getWorkoutType(workoutType: String?): WorkoutTypeEnum {
    return when (workoutType) {
        "Weight Training" -> WorkoutTypeEnum.WEIGHT_TRAINING
        "Calisthenics" -> WorkoutTypeEnum.CALISTHENICS
        "Cardio" -> WorkoutTypeEnum.CARDIO
        else -> WorkoutTypeEnum.WEIGHT_TRAINING
    }
}

fun getWorkoutIcon(workoutType: String?): Int {
    val workoutTypeValue = getWorkoutType(workoutType = workoutType)
    return when (workoutTypeValue) {
        WorkoutTypeEnum.WEIGHT_TRAINING -> R.drawable.icon_dumbell
        WorkoutTypeEnum.CALISTHENICS -> R.drawable.icon_person
        WorkoutTypeEnum.CARDIO -> R.drawable.icon_sprinting_person
    }
}

fun <T : TypedRealmObject> MutableRealm.getLatestResult(
    searchableClass: KClass<T>
): T? {
    // queries realm db to find a 'frozen' result
    // frozen meaning: not a live result
    // next we find the latest version of the result
    // getting the live result
    // must be called within a realm.write {} or other MutableRealm fn
    val frozenResults = this.query(searchableClass).find().firstOrNull() ?: return null
    return this.findLatest(frozenResults)
}

fun <T : TypedRealmObject> MutableRealm.GetLatestQueryViaId(
    searchableClass: KClass<T>,
    query: String,
    objectId: ObjectId
): T? {
    // queries realm db to find a 'frozen' result
    // frozen meaning: not a live result
    // next we find the latest version of the result
    // getting the live result
    // must be called within a realm.write {} or other MutableRealm fn
    val frozenResults = this
        .query(searchableClass, query, objectId)
        .find()
        .firstOrNull() ?: return null
    return this.findLatest(frozenResults)
}
