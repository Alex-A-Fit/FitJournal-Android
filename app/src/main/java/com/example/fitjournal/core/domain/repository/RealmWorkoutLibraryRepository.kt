package com.example.fitjournal.core.domain.repository

import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary

interface RealmWorkoutLibraryRepository {
    suspend fun addMockDataToRealm()
    suspend fun getRealmWorkoutLibraryList(): List<RealmWorkoutLibrary>
    suspend fun addWorkoutLibraryItemToRealmDb(
        realmWorkoutLibraryItem: RealmWorkoutLibrary
    ): Boolean

    suspend fun updateWorkoutLibraryItemToRealmDb(
        updatedRealmWorkoutLibraryItem: RealmWorkoutLibrary
    ): Boolean

    suspend fun deleteWorkoutEntryFromRealmDb(
        libraryWorkoutName: String
    ): Boolean
}
