package com.example.fitjournal.core.domain.repository

import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import io.realm.kotlin.query.RealmQuery

interface RealmRepository {
    suspend fun addMockDataToRealm()
    suspend fun getWorkoutsFromRealmDb(): RealmQuery<RealmWorkoutEntry>
}
