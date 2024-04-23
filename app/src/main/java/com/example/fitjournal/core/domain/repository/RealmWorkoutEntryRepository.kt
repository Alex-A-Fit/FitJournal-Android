package com.example.fitjournal.core.domain.repository

import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry

interface RealmWorkoutEntryRepository {
    suspend fun addMockDataToRealm()
    suspend fun getRealmWorkoutEntryList(): List<RealmWorkoutEntry>
    suspend fun addSingleWorkoutEntryToRealmDb(
        realmWorkoutEntry: RealmWorkoutEntry
    ): Boolean

    suspend fun updateSingleWorkoutEntryToRealmDb(
        updatedRealmWorkoutEntry: RealmWorkoutEntry
    ): Boolean

    suspend fun deleteWorkoutEntryFromRealmDb(
        realmWorkoutEntry: RealmWorkoutEntry
    ): Boolean
}
