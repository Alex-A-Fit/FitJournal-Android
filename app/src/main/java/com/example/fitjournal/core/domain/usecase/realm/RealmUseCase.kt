package com.example.fitjournal.core.domain.usecase.realm

data class RealmUseCase(
    val createMockDataInRealmUseCase: CreateMockDataInRealmUseCase,
    val getMockDataUseCase: GetMockDataUseCase,
    val convertDatabaseRealmWorkoutEntryToUiUseCase: ConvertDatabaseRealmWorkoutEntryToUiUseCase
)
