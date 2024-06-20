package com.alexafit.fitjournal.core.domain.usecase.realm.workout

data class RealmWorkoutEntryUseCase(
    // GET ALL ENTRIES AS LIST
    val getRealmWorkoutEntryList: GetRealmWorkoutEntryList,

    // GET ALL ENTRIES AS LIST BASED ON WORKOUT NAME
    val getRealmWorkoutEntryListWithName: GetRealmWorkoutEntryListWithNameUseCase,

    // GET SINGLE ENTRY
    val getSingleRealmWorkoutEntry: GetSingleRealmWorkoutEntry,

    // ADD
    val addSingleWorkoutEntryToRealmDbUseCase: AddSingleWorkoutEntryToRealmDbUseCase,

    // UPDATE
    val updateSingleWorkoutEntryToRealmDbUseCase: UpdateSingleWorkoutEntryToRealmDbUseCase,

    // DELETE
    val deleteWorkoutEntryFromRealmDbUseCase: DeleteWorkoutEntryFromRealmDbUseCase,

    // SYNC with ViewModel
    val syncWorkoutEntryDbWithViewModelUseCase: SyncWorkoutEntryDbWithViewModelUseCase
)
