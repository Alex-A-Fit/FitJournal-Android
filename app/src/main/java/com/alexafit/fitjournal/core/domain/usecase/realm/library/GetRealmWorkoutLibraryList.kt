package com.alexafit.fitjournal.core.domain.usecase.realm.library

import com.alexafit.fitjournal.core.domain.mapper.mapToWorkoutLibraryModel
import com.alexafit.fitjournal.core.domain.model.WorkoutLibraryModel
import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import javax.inject.Inject

class GetRealmWorkoutLibraryList @Inject constructor(
    private val realmWorkoutLibraryRepository: RealmWorkoutLibraryRepository
) {
    suspend operator fun invoke(): List<WorkoutLibraryModel> {
        val libraryList = realmWorkoutLibraryRepository.getRealmWorkoutLibraryList()
        return libraryList.map {
            it.mapToWorkoutLibraryModel()
        }
    }
}
