package com.example.fitjournal.core.domain.usecase.realm

import com.example.fitjournal.core.domain.repository.RealmRepository
import javax.inject.Inject

class CreateMockDataInRealmUseCase @Inject constructor(
    private val realmRepository: RealmRepository
) {
    suspend operator fun invoke() {
        realmRepository.addMockDataToRealm()
    }
}
