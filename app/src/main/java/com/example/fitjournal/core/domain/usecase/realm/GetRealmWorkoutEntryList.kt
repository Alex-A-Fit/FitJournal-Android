package com.example.fitjournal.core.domain.usecase.realm

import com.example.fitjournal.core.data.model.realmdb.RealmWorkoutEntry
import com.example.fitjournal.core.domain.repository.RealmRepository
import javax.inject.Inject

class GetRealmWorkoutEntryList @Inject constructor(
    private val realmRepository: RealmRepository
) {
    suspend operator fun invoke(): List<RealmWorkoutEntry> {
        return realmRepository.getRealmWorkoutEntryList()
    }
}
