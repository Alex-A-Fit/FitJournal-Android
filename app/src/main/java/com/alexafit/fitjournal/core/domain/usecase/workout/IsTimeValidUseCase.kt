package com.alexafit.fitjournal.core.domain.usecase.workout

import com.alexafit.fitjournal.core.domain.model.TimeModel
import javax.inject.Inject

class IsTimeValidUseCase @Inject constructor() {
    operator fun invoke(timeModel: TimeModel): Boolean {
        return !(timeModel.hours == "00" && timeModel.minutes == "00" && timeModel.seconds == "00")
    }
}
