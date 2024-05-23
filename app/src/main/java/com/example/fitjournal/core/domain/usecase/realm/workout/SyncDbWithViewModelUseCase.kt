package com.example.fitjournal.core.domain.usecase.realm.workout

import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import javax.inject.Inject

class SyncDbWithViewModelUseCase @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    // checking to see if we need to fetch new data and updating
    // the respective boolean if we do fetch to not fetch again
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