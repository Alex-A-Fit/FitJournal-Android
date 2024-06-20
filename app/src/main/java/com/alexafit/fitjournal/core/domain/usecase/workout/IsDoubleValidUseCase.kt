package com.alexafit.fitjournal.core.domain.usecase.workout

import javax.inject.Inject

class IsDoubleValidUseCase @Inject constructor() {
    operator fun invoke(input: String): Boolean {
        return input.toDoubleOrNull() != null
    }
}
