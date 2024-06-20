package com.alexafit.fitjournal.core.domain.usecase.realm.workout

import com.alexafit.fitjournal.core.data.model.realmdb.workout.RealmWorkoutEntry
import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import javax.inject.Inject

class UpdateSingleWorkoutEntryToRealmDbUseCase @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    suspend operator fun invoke(
        updatedRealmWorkoutEntry: RealmWorkoutEntry
    ): Boolean {
        return realmWorkoutEntryRepository.updateSingleWorkoutEntryToRealmDb(
            updatedRealmWorkoutEntry = updatedRealmWorkoutEntry
        )
    }
}
