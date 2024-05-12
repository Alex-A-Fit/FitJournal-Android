package com.example.fitjournal.core.data.di

import com.example.fitjournal.core.data.repository.RealmWorkoutEntryRepositoryImpl
import com.example.fitjournal.core.data.repository.RealmWorkoutLibraryRepositoryImpl
import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import com.example.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import com.example.fitjournal.core.domain.usecase.realm.library.AddSingleLibraryItemToRealmDbUseCase
import com.example.fitjournal.core.domain.usecase.realm.library.CreateMockDataOfRealmWorkoutLibraryUseCase
import com.example.fitjournal.core.domain.usecase.realm.library.DeleteLibraryItemFromRealmDbUseCase
import com.example.fitjournal.core.domain.usecase.realm.library.GetRealmWorkoutLibraryList
import com.example.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
import com.example.fitjournal.core.domain.usecase.realm.library.UpdateLibraryItemInRealmDbUseCase
import com.example.fitjournal.core.domain.usecase.realm.workout.AddSingleWorkoutEntryToRealmDbUseCase
import com.example.fitjournal.core.domain.usecase.realm.workout.CreateMockDataOfRealmWorkoutEntryUseCase
import com.example.fitjournal.core.domain.usecase.realm.workout.DeleteWorkoutEntryFromRealmDbUseCase
import com.example.fitjournal.core.domain.usecase.realm.workout.GetRealmWorkoutEntryList
import com.example.fitjournal.core.domain.usecase.realm.workout.GetSingleRealmWorkoutEntry
import com.example.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.example.fitjournal.core.domain.usecase.realm.workout.UpdateSingleWorkoutEntryToRealmDbUseCase
import com.example.fitjournal.core.domain.usecase.workout.AddOrSubtractDoublesUseCase
import com.example.fitjournal.core.domain.usecase.workout.AddOrSubtractIntegersUseCase
import com.example.fitjournal.core.domain.usecase.workout.AdjustMandatoryTimeValuesUseCase
import com.example.fitjournal.core.domain.usecase.workout.AdjustTimeValuesUseCase
import com.example.fitjournal.core.domain.usecase.workout.EditWorkoutUseCase
import com.example.fitjournal.core.domain.usecase.workout.IsDoubleValidUseCase
import com.example.fitjournal.core.domain.usecase.workout.IsIntegerValidUseCase
import com.example.fitjournal.core.domain.usecase.workout.IsTimeValidUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRealmWorkoutEntryRepository(): RealmWorkoutEntryRepository {
        return RealmWorkoutEntryRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRealmWorkoutLibraryRepository(): RealmWorkoutLibraryRepository {
        return RealmWorkoutLibraryRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRealmWorkoutEntryUseCase(
        realmWorkoutEntryRepository: RealmWorkoutEntryRepository
    ): RealmWorkoutEntryUseCase {
        return RealmWorkoutEntryUseCase(
            createMockDataOfRealmWorkoutEntryUseCase = CreateMockDataOfRealmWorkoutEntryUseCase(
                realmWorkoutEntryRepository = realmWorkoutEntryRepository
            ),
            getRealmWorkoutEntryList = GetRealmWorkoutEntryList(
                realmWorkoutEntryRepository = realmWorkoutEntryRepository
            ),
            addSingleWorkoutEntryToRealmDbUseCase = AddSingleWorkoutEntryToRealmDbUseCase(
                realmWorkoutEntryRepository = realmWorkoutEntryRepository
            ),
            updateSingleWorkoutEntryToRealmDbUseCase = UpdateSingleWorkoutEntryToRealmDbUseCase(
                realmWorkoutEntryRepository = realmWorkoutEntryRepository
            ),
            deleteWorkoutEntryFromRealmDbUseCase = DeleteWorkoutEntryFromRealmDbUseCase(
                realmWorkoutEntryRepository = realmWorkoutEntryRepository
            ),
            getSingleRealmWorkoutEntry = GetSingleRealmWorkoutEntry(
                realmWorkoutEntryRepository = realmWorkoutEntryRepository
            )
        )
    }

    @Provides
    @Singleton
    fun provideRealmWorkoutLibraryUseCase(
        realmWorkoutLibraryRepository: RealmWorkoutLibraryRepository
    ): RealmWorkoutLibraryUseCase {
        return RealmWorkoutLibraryUseCase(
            createMockDataOfRealmWorkoutLibraryUseCase = CreateMockDataOfRealmWorkoutLibraryUseCase(
                realmWorkoutLibraryRepository = realmWorkoutLibraryRepository
            ),
            getRealmWorkoutLibraryList = GetRealmWorkoutLibraryList(
                realmWorkoutLibraryRepository = realmWorkoutLibraryRepository
            ),
            addSingleLibraryItemToRealmDbUseCase = AddSingleLibraryItemToRealmDbUseCase(
                realmWorkoutLibraryRepository = realmWorkoutLibraryRepository
            ),
            updateLibraryItemInRealmDbUseCase = UpdateLibraryItemInRealmDbUseCase(
                realmWorkoutLibraryRepository = realmWorkoutLibraryRepository
            ),
            deleteLibraryItemFromRealmDbUseCase = DeleteLibraryItemFromRealmDbUseCase(
                realmWorkoutLibraryRepository = realmWorkoutLibraryRepository
            )
        )
    }

    @Provides
    @Singleton
    fun provideEditWorkoutUseCase(): EditWorkoutUseCase {
        return EditWorkoutUseCase(
            addOrSubtractDoublesUseCase = AddOrSubtractDoublesUseCase(),
            addOrSubtractIntegersUseCase = AddOrSubtractIntegersUseCase(),
            isIntegerValidUseCase = IsIntegerValidUseCase(),
            isDoubleValidUseCase = IsDoubleValidUseCase(),
            isTimeValidUseCase = IsTimeValidUseCase(),
            adjustTimeValuesUseCase = AdjustTimeValuesUseCase(),
            adjustMandatoryTimeValuesUseCase = AdjustMandatoryTimeValuesUseCase()
        )
    }
}
