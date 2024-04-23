package com.example.fitjournal.core.domain.usecase.realm

import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import javax.inject.Inject

class AddSingleWorkoutEntryToRealmDbUseCase @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    suspend operator fun invoke(realmWorkoutEntry: RealmWorkoutEntry): Boolean {
        return realmWorkoutEntryRepository.addSingleWorkoutEntryToRealmDb(realmWorkoutEntry = realmWorkoutEntry)
    }
}
