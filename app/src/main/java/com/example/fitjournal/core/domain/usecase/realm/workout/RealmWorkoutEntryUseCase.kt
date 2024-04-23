package com.example.fitjournal.core.domain.usecase.realm.workout

data class RealmWorkoutEntryUseCase(
    val createMockDataOfRealmWorkoutEntryUseCase: CreateMockDataOfRealmWorkoutEntryUseCase,

    // GET
    val getRealmWorkoutEntryList: GetRealmWorkoutEntryList,

    // ADD
    val addSingleWorkoutEntryToRealmDbUseCase: AddSingleWorkoutEntryToRealmDbUseCase,

    // UPDATE
    val updateSingleWorkoutEntryToRealmDbUseCase: UpdateSingleWorkoutEntryToRealmDbUseCase,

    // DELETE
    val deleteWorkoutEntryFromRealmDbUseCase: DeleteWorkoutEntryFromRealmDbUseCase
)
