package com.example.fitjournal.core.domain.usecase.realm.workout

import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import javax.inject.Inject

class SyncWorkoutEntryDbWithViewModelUseCase @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    // checking to see if we need to fetch new data and if so, updating
    // the respective boolean on repo side to not fetch again until db changes
    operator fun invoke(): Boolean {
        val shouldSyncOccur = realmWorkoutEntryRepository.shouldViewModelFetchRealmData
        return if (shouldSyncOccur) {
            realmWorkoutEntryRepository.updateShouldViewModelFetchRealmData(false)
            true
        } else {
            false
        }
    }
}
