package com.alexafit.fitjournal.core.domain.usecase.realm.library

import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import com.alexafit.fitjournal.library.domain.model.UpdateWorkoutLibraryModel
import javax.inject.Inject

class UpdateLibraryItemInRealmDbUseCase @Inject constructor(
    private val realmWorkoutLibraryRepository: RealmWorkoutLibraryRepository
) {
    suspend operator fun invoke(libraryItem: UpdateWorkoutLibraryModel): Boolean {
        return realmWorkoutLibraryRepository.updateWorkoutLibraryItemToRealmDb(
            updatedRealmWorkoutLibraryItem = libraryItem
        )
    }
}
