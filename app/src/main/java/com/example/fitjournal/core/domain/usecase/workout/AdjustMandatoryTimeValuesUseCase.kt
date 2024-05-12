package com.example.fitjournal.core.domain.usecase.workout

import com.example.fitjournal.core.domain.model.TimeModel
import javax.inject.Inject

class AdjustMandatoryTimeValuesUseCase @Inject constructor() {
    operator fun invoke(timeModel: TimeModel): TimeModel {
        val timeModelList = mutableListOf(timeModel.hours, timeModel.minutes, timeModel.seconds)
        timeModelList.forEachIndexed { index, time ->
            when {
                time.length == 1 -> timeModelList[index] = "0$time"
                time.isEmpty() -> timeModelList[index] = "00"
            }
        }
        return TimeModel(
            hours = timeModelList[0],
            minutes = timeModelList[1],
            seconds = timeModelList[2]
        )
    }
}
