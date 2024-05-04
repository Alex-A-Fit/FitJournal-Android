package com.example.fitjournal.core.domain.usecase.workout

import javax.inject.Inject

class IsIntegerValidUseCase @Inject constructor() {
    operator fun invoke(input: String): Boolean {
        return input.toIntOrNull() != null
    }
}
