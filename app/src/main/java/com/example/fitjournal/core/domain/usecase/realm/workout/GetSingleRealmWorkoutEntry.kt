package com.example.fitjournal.core.domain.usecase.realm.workout

import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import javax.inject.Inject

class GetSingleRealmWorkoutEntry @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    suspend operator fun invoke(workoutId: String): WorkoutModel? {
        val realmResult = realmWorkoutEntryRepository.getSingleRealmWorkoutEntry(workoutId)
        return if (realmResult != null) {
            convertRealmWorkoutEntryToWorkoutModelUseCase(listOf(realmResult)).firstOrNull()
        } else {
            null
        }
    }
}
