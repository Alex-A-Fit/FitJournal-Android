package com.alexafit.fitjournal.core.domain.usecase.realm.workout

import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import javax.inject.Inject

class DeleteWorkoutEntryFromRealmDbUseCase @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    suspend operator fun invoke(
        workoutId: String
    ): Boolean {
        return realmWorkoutEntryRepository.deleteWorkoutEntryFromRealmDb(
            realmWorkoutId = workoutId
        )
    }
}
