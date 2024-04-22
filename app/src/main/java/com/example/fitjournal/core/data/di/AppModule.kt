package com.example.fitjournal.core.data.di

import com.example.fitjournal.core.data.repository.RealmRepositoryImpl
import com.example.fitjournal.core.domain.repository.RealmRepository
import com.example.fitjournal.core.domain.usecase.realm.AddSingleWorkoutEntryToRealmDbUseCase
import com.example.fitjournal.core.domain.usecase.realm.ConvertRealmWorkoutEntryToWorkoutModelUseCase
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
    fun provideRealmRepository(): RealmRepository {
        return RealmRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideRealmUseCase(
        realmRepository: RealmRepository
    ): RealmUseCase {
        return RealmUseCase(
            createMockDataInRealmUseCase = CreateMockDataInRealmUseCase(
                realmRepository = realmRepository
            ),
            getRealmWorkoutEntryList = GetRealmWorkoutEntryList(
                realmRepository = realmRepository
            ),
            addSingleWorkoutEntryToRealmDbUseCase = AddSingleWorkoutEntryToRealmDbUseCase(
                realmRepository = realmRepository
            ),
            updateSingleWorkoutEntryToRealmDbUseCase = UpdateSingleWorkoutEntryToRealmDbUseCase(
                realmRepository = realmRepository
            ),
            deleteWorkoutEntryFromRealmDbUseCase = DeleteWorkoutEntryFromRealmDbUseCase(
                realmRepository = realmRepository
            ),
            convertRealmWorkoutEntryToWorkoutModelUseCase = ConvertRealmWorkoutEntryToWorkoutModelUseCase()
        )
    }
}
