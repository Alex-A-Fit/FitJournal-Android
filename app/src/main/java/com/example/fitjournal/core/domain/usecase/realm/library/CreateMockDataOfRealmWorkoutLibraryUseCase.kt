package com.example.fitjournal.core.domain.usecase.realm.library

import com.example.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import javax.inject.Inject

class CreateMockDataOfRealmWorkoutLibraryUseCase @Inject constructor(
    private val realmWorkoutLibraryRepository: RealmWorkoutLibraryRepository
) {
    suspend operator fun invoke() {
        realmWorkoutLibraryRepository.addMockDataToRealm()
    }
}
