package com.example.fitjournal.core.util.extensions

fun String.toDoubleOrZero(): Double {
    return if (this.isBlank()) 0.0 else this.toDoubleOrNull() ?: 0.0
}

fun String.toIntOrZero(): Int {
    return if (this.isBlank()) 0 else this.toIntOrNull() ?: 0
}
