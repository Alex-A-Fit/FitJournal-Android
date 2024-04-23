package com.example.fitjournal.core.presentation.model.enums

import com.example.fitjournal.R

enum class WorkoutTypeEnum(val stringId: Int) {
    WEIGHT_TRAINING(stringId = R.string.title_weight_training) {
        override fun workoutTitle() = R.string.text_weightlifting_menu_title
    },
    CALISTHENICS(stringId = R.string.title_calisthenics) {
        override fun workoutTitle() = R.string.text_calisthenics_menu_title
    },
    CARDIO(stringId = R.string.title_cardio) {
        override fun workoutTitle() = R.string.text_cardio_menu_title
    };

    abstract fun workoutTitle(): Int
}
