package com.example.fitjournal.core.data.di

import com.example.fitjournal.core.data.repository.RealmRepositoryImpl
import com.example.fitjournal.core.domain.repository.RealmRepository
import com.example.fitjournal.core.domain.usecase.realm.ConvertDatabaseRealmWorkoutEntryToUiUseCase
import com.example.fitjournal.core.domain.usecase.realm.CreateMockDataInRealmUseCase
import com.example.fitjournal.core.domain.usecase.realm.GetMockDataUseCase
import com.example.fitjournal.core.domain.usecase.realm.RealmUseCase
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
            getMockDataUseCase = GetMockDataUseCase(
                realmRepository = realmRepository
            ),
            convertDatabaseRealmWorkoutEntryToUiUseCase = ConvertDatabaseRealmWorkoutEntryToUiUseCase()
        )
    }
}
