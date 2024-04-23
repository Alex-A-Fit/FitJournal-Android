package com.example.fitjournal.core.domain.usecase.realm.library

import com.example.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.example.fitjournal.core.domain.model.WorkoutLibraryModel
import com.example.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import javax.inject.Inject

class UpdateLibraryItemInRealmDbUseCase @Inject constructor(
    private val realmWorkoutLibraryRepository: RealmWorkoutLibraryRepository
) {
    suspend operator fun invoke(libraryItem: WorkoutLibraryModel): Boolean {
        val updatedRealmWorkoutLibraryItem = RealmWorkoutLibrary().apply {
            name = libraryItem.name
            type = libraryItem.workoutType
        }
        return realmWorkoutLibraryRepository.updateWorkoutLibraryItemToRealmDb(
            updatedRealmWorkoutLibraryItem = updatedRealmWorkoutLibraryItem
        )
    }
}
