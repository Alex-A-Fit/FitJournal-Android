package com.alexafit.fitjournal.core.domain.usecase.realm.library

import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import javax.inject.Inject

class CreateMockDataOfRealmWorkoutLibraryUseCase @Inject constructor(
    private val realmWorkoutLibraryRepository: RealmWorkoutLibraryRepository
) {
    suspend operator fun invoke() {
        realmWorkoutLibraryRepository.addMockDataToRealm()
    }
}
