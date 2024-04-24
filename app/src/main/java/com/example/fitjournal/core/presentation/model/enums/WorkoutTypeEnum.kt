package com.example.fitjournal.core.presentation.model.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R

enum class WorkoutTypeEnum(val stringId: Int) {
    WEIGHT_TRAINING(stringId = R.string.title_weight_training) {
        @Composable
        override fun workoutTitle() = stringResource(id = R.string.text_weightlifting_menu_title)
    },
    CALISTHENICS(stringId = R.string.title_calisthenics) {
        @Composable
        override fun workoutTitle() = stringResource(id = R.string.text_calisthenics_menu_title)
    },
    CARDIO(stringId = R.string.title_cardio) {
        @Composable
        override fun workoutTitle() = stringResource(id = R.string.text_cardio_menu_title)
    };

    @Composable
    abstract fun workoutTitle(): String
}
