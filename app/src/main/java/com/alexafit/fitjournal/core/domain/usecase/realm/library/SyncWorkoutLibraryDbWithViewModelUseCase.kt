package com.alexafit.fitjournal.core.domain.usecase.realm.library

import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import javax.inject.Inject

class SyncWorkoutLibraryDbWithViewModelUseCase @Inject constructor(
    private val realmWorkoutLibraryRepository: RealmWorkoutLibraryRepository
) {
    // checking to see if we need to fetch new data and if so, updating
    // the respective boolean on repo side to not fetch again until db changes
    operator fun invoke(): Boolean {
        val shouldSyncOccur = realmWorkoutLibraryRepository.shouldViewModelFetchRealmData
        return if (shouldSyncOccur) {
            realmWorkoutLibraryRepository.updateShouldViewModelFetchRealmData(false)
            true
        } else {
            false
        }
    }
}
