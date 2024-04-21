package com.example.fitjournal.core.domain.usecase.realm

import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.domain.repository.RealmRepository
import io.realm.kotlin.query.RealmQuery
import javax.inject.Inject

class GetMockDataUseCase @Inject constructor(
    private val realmRepository: RealmRepository
) {
    suspend operator fun invoke(): RealmQuery<RealmWorkoutEntry> {
        return realmRepository.getWorkoutsFromRealmDb()
    }
}
