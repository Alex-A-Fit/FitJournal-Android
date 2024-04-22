package com.example.fitjournal.core.domain.usecase.realm

data class RealmUseCase(
    val createMockDataInRealmUseCase: CreateMockDataInRealmUseCase,

    // GET
    val getRealmWorkoutEntryList: GetRealmWorkoutEntryList,

    // ADD
    val addSingleWorkoutEntryToRealmDbUseCase: AddSingleWorkoutEntryToRealmDbUseCase,

    // UPDATE
    val updateSingleWorkoutEntryToRealmDbUseCase: UpdateSingleWorkoutEntryToRealmDbUseCase,

    // DELETE
    val deleteWorkoutEntryFromRealmDbUseCase: DeleteWorkoutEntryFromRealmDbUseCase
)
