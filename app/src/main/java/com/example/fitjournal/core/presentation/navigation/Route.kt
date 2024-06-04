package com.example.fitjournal.core.presentation.navigation

object Route {
    const val HOME_SCREEN = "homeScreen"
    const val EDIT_JOURNAL_SCREEN = "editJournalScreen"
    const val WORKOUT_LIBRARY_SCREEN = "workoutLibraryScreen"
    const val WORKOUT_STATISTICS_SCREEN = "workoutStatisticsScreen"
    const val WORKOUT_STATISTICS_DETAILS_SCREEN = "workoutStatisticsDetailsScreen"
    const val LOTTIE_INTRO = "lottieIntroAnimation"
    const val ADD_WORKOUT_SCREEN = "addWorkoutScreen"
    const val ADD_WORKOUT_DETAILS_SCREEN = "addWorkoutDetailsScreen"
}

object Arguments {
    const val WORKOUT_ID = "/{workoutId}"
    const val WORKOUT_NAME = "/{workoutName}"
    const val WORKOUT_TYPE = "/{workoutType}"
    const val WORKOUT_DATE = "/{workoutDate}"
}
