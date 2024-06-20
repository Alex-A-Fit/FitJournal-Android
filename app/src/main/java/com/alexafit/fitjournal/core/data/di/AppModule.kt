package com.alexafit.fitjournal.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.alexafit.fitjournal.core.data.repository.OnboardingTutorialRepositoryImpl
import com.alexafit.fitjournal.core.data.repository.RealmWorkoutEntryRepositoryImpl
import com.alexafit.fitjournal.core.data.repository.RealmWorkoutLibraryRepositoryImpl
import com.alexafit.fitjournal.core.domain.repository.OnboardingTutorialRepository
import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutEntryRepository
import com.alexafit.fitjournal.core.domain.repository.RealmWorkoutLibraryRepository
import com.alexafit.fitjournal.core.domain.usecase.editworkoutdialog.CreateModelForEditWorkoutDialogUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.library.AddSingleLibraryItemToRealmDbUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.library.CreateMockDataOfRealmWorkoutLibraryUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.library.DeleteLibraryItemFromRealmDbUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.library.GetRealmWorkoutLibraryList
import com.alexafit.fitjournal.core.domain.usecase.realm.library.RealmWorkoutLibraryUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.library.SyncWorkoutLibraryDbWithViewModelUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.library.UpdateLibraryItemInRealmDbUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.workout.AddSingleWorkoutEntryToRealmDbUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.workout.DeleteWorkoutEntryFromRealmDbUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.workout.GetRealmWorkoutEntryList
import com.alexafit.fitjournal.core.domain.usecase.realm.workout.GetRealmWorkoutEntryListWithNameUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.workout.GetSingleRealmWorkoutEntry
import com.alexafit.fitjournal.core.domain.usecase.realm.workout.RealmWorkoutEntryUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.workout.SyncWorkoutEntryDbWithViewModelUseCase
import com.alexafit.fitjournal.core.domain.usecase.realm.workout.UpdateSingleWorkoutEntryToRealmDbUseCase
import com.alexafit.fitjournal.core.domain.usecase.workout.AddOrSubtractDoublesUseCase
import com.alexafit.fitjournal.core.domain.usecase.workout.AddOrSubtractIntegersUseCase
import com.alexafit.fitjournal.core.domain.usecase.workout.AdjustMandatoryTimeValuesUseCase
import com.alexafit.fitjournal.core.domain.usecase.workout.AdjustTimeValuesUseCase
import com.alexafit.fitjournal.core.domain.usecase.workout.EditWorkoutUseCase
import com.alexafit.fitjournal.core.domain.usecase.workout.IsDoubleValidUseCase
import com.alexafit.fitjournal.core.domain.usecase.workout.IsIntegerValidUseCase
import com.alexafit.fitjournal.core.domain.usecase.workout.IsTimeValidUseCase
import com.alexafit.fitjournal.statistics.domain.usecase.GetWorkoutsByTimeSelectedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
            ),
            syncWorkoutEntryDbWithViewModelUseCase = SyncWorkoutEntryDbWithViewModelUseCase(
                realmWorkoutEntryRepository = realmWorkoutEntryRepository
            ),
            getRealmWorkoutEntryListWithName = GetRealmWorkoutEntryListWithNameUseCase(
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
            ),
            syncRealmWorkoutLibraryUseCase = SyncWorkoutLibraryDbWithViewModelUseCase(
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

    @Provides
    fun provideCreateModelForEditWorkoutDialogUseCase(): CreateModelForEditWorkoutDialogUseCase {
        return CreateModelForEditWorkoutDialogUseCase()
    }

    @Provides
    fun provideGetWorkoutsByTimeUseCase(): GetWorkoutsByTimeSelectedUseCase = GetWorkoutsByTimeSelectedUseCase()

    private const val DATASTORE_PREFERENCE_NAME = "onboarding_preference"

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        val datastore = PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(DATASTORE_PREFERENCE_NAME)
        }
        return datastore
    }

    private val Context.datastore by preferencesDataStore(name = DATASTORE_PREFERENCE_NAME)

    @Singleton
    @Provides
    fun providePreferencesStorage(@ApplicationContext context: Context): OnboardingTutorialRepository =
        OnboardingTutorialRepositoryImpl(context.datastore)
}
