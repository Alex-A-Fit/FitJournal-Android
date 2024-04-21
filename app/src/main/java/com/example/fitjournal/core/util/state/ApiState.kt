package com.example.fitjournal.core.util.state

/*
    A generic state class to handle data via DBs or api
 */
sealed class ApiState<out T> {
    data class Success<out T>(val data: T) : ApiState<T>()
    data object Empty : ApiState<Nothing>()
    data class Error<out T>(val reasonForError: T) : ApiState<T>()
}
