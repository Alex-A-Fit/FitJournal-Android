package com.alexafit.fitjournal.core.domain.repository

import com.alexafit.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.alexafit.fitjournal.library.domain.model.UpdateWorkoutLibraryModel

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
