package com.example.fitjournal.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface OnboardingTutorialRepository {
    var hasUserSeenTutorial: Flow<Boolean?>
    suspend fun setUserHasSeenTutorial()
    suspend fun getHasUserSeenTutorial(): Flow<Boolean?>
}
