package com.example.fitjournal.core.data.util

import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.model.enums.WorkoutTypeEnum
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.types.TypedRealmObject
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
    // frozen means: not a live result
    // next we find the latest version of the result via findLatest
    // this creates a live result
    // must be called within a realm.write {} or other MutableRealm fn
    val frozenResults = this.query(searchableClass).find().firstOrNull() ?: return null
    return this.findLatest(frozenResults)
}

fun <T : TypedRealmObject> MutableRealm.getLatestResultViaQuery(
    searchableClass: KClass<T>,
    query: String,
    queryValue: Any
): T? {
    // queries realm db to find a 'frozen' result via query inputted
    // queryValue is value used when comparing query
    // frozen means: not a live result
    // next we find the latest version of the result
    // this creates a live result
    // must be called within a realm.write {} or other MutableRealm fn
    val frozenResults = this
        .query(searchableClass, query, queryValue)
        .find()
        .firstOrNull() ?: return null
    return this.findLatest(frozenResults)
}
