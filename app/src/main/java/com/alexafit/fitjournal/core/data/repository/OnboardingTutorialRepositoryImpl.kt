package com.alexafit.fitjournal.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.alexafit.fitjournal.core.domain.repository.OnboardingTutorialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val DATA_STORE_ONBOARDING_TOKEN_KEY = booleanPreferencesKey("pref_onboarding_key")

class OnboardingTutorialRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : OnboardingTutorialRepository {
    override var hasUserSeenTutorial: Flow<Boolean?> =
        dataStore.data.map { it[DATA_STORE_ONBOARDING_TOKEN_KEY] }

    override suspend fun setUserHasSeenTutorial() {
        dataStore.edit { preferences ->
            preferences[DATA_STORE_ONBOARDING_TOKEN_KEY] = true
        }
    }

    override suspend fun getHasUserSeenTutorial(): Flow<Boolean?> {
        return hasUserSeenTutorial
    }
}
