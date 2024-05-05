package com.example.fitjournal.core.domain.usecase.realm.workout

import com.example.fitjournal.core.domain.model.WorkoutModel
import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import com.example.fitjournal.core.util.state.UiState
import javax.inject.Inject

class GetSingleRealmWorkoutEntry @Inject constructor(
    private val realmWorkoutEntryRepository: RealmWorkoutEntryRepository
) {
    suspend operator fun invoke(workoutId: String): UiState<WorkoutModel> {
        val realmResult = realmWorkoutEntryRepository.getSingleRealmWorkoutEntry(workoutId)
        return if (realmResult != null) {
            val workout = convertRealmWorkoutEntryToWorkoutModelUseCase(listOf(realmResult)).firstOrNull()
            if (workout != null) UiState.Success(workout) else UiState.Empty
        } else {
            UiState.Empty
        }
    }
}
