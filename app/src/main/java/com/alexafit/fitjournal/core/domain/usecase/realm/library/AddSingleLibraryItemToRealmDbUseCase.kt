package com.alexafit.fitjournal.core.domain.usecase.realm.library

import com.alexafit.fitjournal.core.data.model.realmdb.library.RealmWorkoutLibrary
import com.alexafit.fitjournal.core.domain.model.WorkoutLibraryModel
import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import javax.inject.Inject

class AddSingleLibraryItemToRealmDbUseCase @Inject constructor(
    private val realmWorkoutLibraryRepository: RealmWorkoutLibraryRepository
) {
    suspend operator fun invoke(workoutLibraryModel: WorkoutLibraryModel): Boolean {
        val realmWorkoutLibraryItem = RealmWorkoutLibrary().apply {
            name = workoutLibraryModel.name
            type = workoutLibraryModel.workoutType
        }
        return realmWorkoutLibraryRepository.addWorkoutLibraryItemToRealmDb(
            realmWorkoutLibraryItem = realmWorkoutLibraryItem
        )
    }
}
