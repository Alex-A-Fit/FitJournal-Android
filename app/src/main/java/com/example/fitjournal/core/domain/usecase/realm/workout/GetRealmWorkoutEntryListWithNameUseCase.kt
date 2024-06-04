package com.example.fitjournal.core.domain.usecase.realm.workout

import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import javax.inject.Inject

class GetRealmWorkoutEntryListWithNameUseCase @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    suspend operator fun invoke(workoutName: String): List<WorkoutModel> {
        val realmList = realmWorkoutEntryRepository.getRealmWorkoutEntryListWithName(workoutName)
        return if (realmList.isNotEmpty()) {
            return convertRealmWorkoutEntryToWorkoutModelUseCase(realmList)
        } else {
            emptyList()
        }
    }
}
