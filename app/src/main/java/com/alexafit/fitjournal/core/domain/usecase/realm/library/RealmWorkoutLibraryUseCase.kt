package com.alexafit.fitjournal.core.domain.usecase.realm.library

data class RealmWorkoutLibraryUseCase(
    val createMockDataOfRealmWorkoutLibraryUseCase: CreateMockDataOfRealmWorkoutLibraryUseCase,

    // GET
    val getRealmWorkoutLibraryList: GetRealmWorkoutLibraryList,

    // ADD
    val addSingleLibraryItemToRealmDbUseCase: AddSingleLibraryItemToRealmDbUseCase,

    // UPDATE
    val updateLibraryItemInRealmDbUseCase: UpdateLibraryItemInRealmDbUseCase,

    // DELETE
    val deleteLibraryItemFromRealmDbUseCase: DeleteLibraryItemFromRealmDbUseCase,

    // SYNC with ViewModel
    val syncRealmWorkoutLibraryUseCase: SyncWorkoutLibraryDbWithViewModelUseCase
)
