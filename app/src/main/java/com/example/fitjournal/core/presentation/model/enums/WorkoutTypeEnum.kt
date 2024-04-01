package com.example.fitjournal.core.presentation.model.enums

import com.example.fitjournal.R

enum class WorkoutTypeEnum(val stringValue: Int) {
    WEIGHT_TRAINING(stringValue = R.string.title_weight_training),
    CALISTHENICS(stringValue = R.string.title_calisthenics),
    CARDIO(stringValue = R.string.title_cardio)
}
