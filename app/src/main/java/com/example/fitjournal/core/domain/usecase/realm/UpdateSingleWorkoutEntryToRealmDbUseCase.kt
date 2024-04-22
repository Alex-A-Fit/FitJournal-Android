package com.example.fitjournal.core.domain.usecase.realm

import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.domain.repository.RealmRepository
import javax.inject.Inject

class UpdateSingleWorkoutEntryToRealmDbUseCase @Inject constructor(
    private val realmRepository: RealmRepository
) {
    suspend operator fun invoke(
        updatedRealmWorkoutEntry: RealmWorkoutEntry
    ): Boolean {
        return realmRepository.updateSingleWorkoutEntryToRealmDb(
            updatedRealmWorkoutEntry = updatedRealmWorkoutEntry
        )
    }
}
