package com.example.fitjournal.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fitjournal.managers.DateManager
import com.example.fitjournal.model.uistate.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(): ViewModel()  {
    var homeScreenState: HomeScreenUiState by mutableStateOf(HomeScreenUiState())
        private set

    init {
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDate = DateManager.formatDate(homeScreenState.currentDate)
            )
        )
    }

    fun getNextDate() {
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDate = DateManager.getNextDate(
                    homeScreenState.currentDate
                )
            )
        )
    }

    fun getPreviousDate() {
        updateHomeScreenState(
            newHomeScreenState = homeScreenState.copy(
                currentDate = DateManager.getPreviousDate(
                    homeScreenState.currentDate
                )
            )
        )
    }

    fun updateHomeScreenState(newHomeScreenState: HomeScreenUiState) {
        homeScreenState = newHomeScreenState
    }
}