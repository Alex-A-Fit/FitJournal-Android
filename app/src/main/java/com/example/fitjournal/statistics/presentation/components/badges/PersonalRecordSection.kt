package com.example.fitjournal.statistics.presentation.components.badges

import androidx.compose.runtime.Composable
import com.example.fitjournal.statistics.domain.model.PersonalRecordAnalytics
import com.example.fitjournal.statistics.domain.model.PersonalRecordType
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum

@Composable
fun PersonalRecordSection(
    personalRecordData: PersonalRecordAnalytics,
    timeRangeOfWorkouts: TimeRangeEnum
) {
    val prData = when (timeRangeOfWorkouts) {
        TimeRangeEnum.WEEK -> {
            personalRecordData.prByWeek
        }
        TimeRangeEnum.MONTH -> {
            personalRecordData.prByMonth
        }
        TimeRangeEnum.YEAR -> {
            personalRecordData.prByYear
        }
        TimeRangeEnum.ALL_TIME -> {
            personalRecordData.prByAllTime
        }
    }
    when (prData) {
        is PersonalRecordType.Calisthenics -> {
            CalisthenicsPrBadges(
                calisthenicsPrData = prData,
                timeRangeEnum = timeRangeOfWorkouts
            )
        }
        is PersonalRecordType.Cardio -> {
            CardioPrBadges(
                cardioPrData = prData,
                timeRangeEnum = timeRangeOfWorkouts
            )
        }
        is PersonalRecordType.WeightTraining -> {
            WeightTrainingPrBadges(
                weightTrainingPrData = prData,
                timeRangeEnum = timeRangeOfWorkouts
            )
        }
        null -> Unit
    }
}
