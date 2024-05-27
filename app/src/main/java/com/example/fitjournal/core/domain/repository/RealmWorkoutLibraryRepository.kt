package com.example.fitjournal.core.domain.repository

import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.library.domain.model.UpdateWorkoutLibraryModel

interface RealmWorkoutLibraryRepository {
    var shouldViewModelFetchRealmData: Boolean
    suspend fun addMockDataToRealm()
    suspend fun getRealmWorkoutLibraryList(): List<RealmWorkoutLibrary>
    suspend fun addWorkoutLibraryItemToRealmDb(
        realmWorkoutLibraryItem: RealmWorkoutLibrary
    ): Boolean

    suspend fun updateWorkoutLibraryItemToRealmDb(
        updatedRealmWorkoutLibraryItem: UpdateWorkoutLibraryModel
    ): Boolean

    suspend fun deleteWorkoutEntryFromRealmDb(
        libraryWorkoutName: String
    ): Boolean
    fun updateShouldViewModelFetchRealmData(shouldFetch: Boolean)
}
