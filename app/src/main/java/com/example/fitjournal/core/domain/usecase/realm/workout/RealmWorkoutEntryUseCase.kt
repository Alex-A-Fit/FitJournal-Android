package com.example.fitjournal.core.domain.usecase.realm.workout

data class RealmWorkoutEntryUseCase(
    val createMockDataOfRealmWorkoutEntryUseCase: CreateMockDataOfRealmWorkoutEntryUseCase,

    // GET ALL ENTRIES AS LIST
    val getRealmWorkoutEntryList: GetRealmWorkoutEntryList,

    // GET SINGLE ENTRY
    val getSingleRealmWorkoutEntry: GetSingleRealmWorkoutEntry,

    // ADD
    val addSingleWorkoutEntryToRealmDbUseCase: AddSingleWorkoutEntryToRealmDbUseCase,

    // UPDATE
    val updateSingleWorkoutEntryToRealmDbUseCase: UpdateSingleWorkoutEntryToRealmDbUseCase,

    // DELETE
    val deleteWorkoutEntryFromRealmDbUseCase: DeleteWorkoutEntryFromRealmDbUseCase
)
