package com.example.fitjournal.core.util.state

/*
    A generic state class to handle Ui Data
 */
sealed class UiState<out T> {

    // a successful ui state is when we have data to show
    data class Success<out T>(val data: T) : UiState<T>()

    // loading is loading; self-explanatory
    data object Loading : UiState<Nothing>()

    // empty is when nothing is returned from data calls
    data object Empty : UiState<Nothing>()

    // none is initialization of Ui; a default starter
    data object None : UiState<Nothing>()

    // error is to provide Error Handling on Ui side
    data object Error : UiState<Nothing>()
}
