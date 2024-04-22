package com.example.fitjournal.core.domain.usecase.realm

data class RealmUseCase(
    val createMockDataInRealmUseCase: CreateMockDataInRealmUseCase,
    val getRealmWorkoutEntryList: GetRealmWorkoutEntryList,
    val convertRealmWorkoutEntryToWorkoutModelUseCase: ConvertRealmWorkoutEntryToWorkoutModelUseCase,
    val addSingleWorkoutEntryToRealmDbUseCase: AddSingleWorkoutEntryToRealmDbUseCase,
    val updateSingleWorkoutEntryToRealmDbUseCase: UpdateSingleWorkoutEntryToRealmDbUseCase,
    val deleteWorkoutEntryFromRealmDbUseCase: DeleteWorkoutEntryFromRealmDbUseCase
)
