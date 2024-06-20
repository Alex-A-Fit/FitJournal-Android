package com.alexafit.fitjournal.core.domain.usecase.realm.library

import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import javax.inject.Inject

class DeleteLibraryItemFromRealmDbUseCase @Inject constructor(
    private val realmWorkoutLibraryRepository: RealmWorkoutLibraryRepository
) {
    suspend operator fun invoke(libraryWorkoutName: String): Boolean {
        return realmWorkoutLibraryRepository
            .deleteWorkoutEntryFromRealmDb(libraryWorkoutName = libraryWorkoutName)
    }
}
