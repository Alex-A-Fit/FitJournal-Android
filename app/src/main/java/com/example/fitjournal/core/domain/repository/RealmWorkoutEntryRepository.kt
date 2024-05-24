package com.example.fitjournal.core.domain.repository

import com.example.fitjournal.core.data.model.realmdb.workout.RealmWorkoutEntry

interface RealmWorkoutEntryRepository {
    var shouldViewModelFetchRealmData: Boolean
    suspend fun addMockDataToRealm()
    suspend fun getRealmWorkoutEntryList(): List<RealmWorkoutEntry>
    suspend fun getSingleRealmWorkoutEntry(workoutId: String): RealmWorkoutEntry?
    suspend fun addSingleWorkoutEntryToRealmDb(
        realmWorkoutEntry: RealmWorkoutEntry
    ): Boolean

    suspend fun updateSingleWorkoutEntryToRealmDb(
        updatedRealmWorkoutEntry: RealmWorkoutEntry
    ): Boolean

    suspend fun deleteWorkoutEntryFromRealmDb(
        realmWorkoutId: String
    ): Boolean

    fun updateShouldViewModelFetchRealmData(shouldFetch: Boolean)
}
