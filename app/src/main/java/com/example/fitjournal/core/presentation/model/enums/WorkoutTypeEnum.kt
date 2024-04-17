package com.example.fitjournal.core.presentation.model.enums

import com.example.fitjournal.R

enum class WorkoutTypeEnum(val stringValue: Int) {
    WEIGHT_TRAINING(stringValue = R.string.title_weight_training) {
        override fun workoutTitle() = R.string.text_weightlifting_menu_icon
    },
    CALISTHENICS(stringValue = R.string.title_calisthenics) {
        override fun workoutTitle() = R.string.text_calisthenics_menu_icon
    },
    CARDIO(stringValue = R.string.title_cardio) {
        override fun workoutTitle() = R.string.text_cardio_menu_icon
    };

    abstract fun workoutTitle(): Int
}
