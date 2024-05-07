package com.example.fitjournal.home.presentation.model.ui

import com.example.fitjournal.core.domain.model.TimeModel

data class CalisthenicsUi(
    val reps: Int? = null,
    val sets: Int? = null,
    val weight: Double? = null,
    val time: TimeModel? = null,
    val name: String,
    val icon: Int
) {
    fun doesRepsAndSetsExist(): Boolean {
        return reps != null && sets != null
    }
}
