package com.example.fitjournal.home.presentation.model.ui

data class CalisthenicsUi(
    val reps: Int? = null,
    val sets: Int? = null,
    val weight: Double? = null,
    val time: String? = null,
    val name: String,
    val icon: Int
)
