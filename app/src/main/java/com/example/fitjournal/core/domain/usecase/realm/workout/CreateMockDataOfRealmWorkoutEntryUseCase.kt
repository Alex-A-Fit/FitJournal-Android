package com.example.fitjournal.core.domain.usecase.realm.workout

import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import javax.inject.Inject

class CreateMockDataOfRealmWorkoutEntryUseCase @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    suspend operator fun invoke() {
        realmWorkoutEntryRepository.addMockDataToRealm()
    }
}
