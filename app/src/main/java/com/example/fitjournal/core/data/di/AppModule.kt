package com.example.fitjournal.core.data.di

import com.example.fitjournal.core.data.repository.RealmWorkoutEntryWorkoutEntryRepositoryImpl
import com.example.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import com.example.fitjournal.core.domain.usecase.realm.AddSingleWorkoutEntryToRealmDbUseCase
import com.example.fitjournal.core.domain.usecase.realm.CreateMockDataInRealmUseCase
import com.example.fitjournal.core.domain.usecase.realm.DeleteWorkoutEntryFromRealmDbUseCase
import com.example.fitjournal.core.domain.usecase.realm.GetRealmWorkoutEntryList
import com.example.fitjournal.core.domain.usecase.realm.RealmUseCase
import com.example.fitjournal.core.domain.usecase.realm.UpdateSingleWorkoutEntryToRealmDbUseCase
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
    fun provideRealmRepository(): RealmWorkoutEntryRepository {
        return RealmWorkoutEntryWorkoutEntryRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRealmUseCase(
        realmWorkoutEntryRepository: RealmWorkoutEntryRepository
    ): RealmUseCase {
        return RealmUseCase(
            createMockDataInRealmUseCase = CreateMockDataInRealmUseCase(
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
            )
        )
    }
}
