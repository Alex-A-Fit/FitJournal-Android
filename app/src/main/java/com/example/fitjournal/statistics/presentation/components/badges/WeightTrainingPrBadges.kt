package com.example.fitjournal.statistics.presentation.components.badges

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.statistics.domain.model.PersonalRecordType
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeightTrainingPrBadges(
    weightTrainingPrData: PersonalRecordType.WeightTraining,
    timeRangeEnum: TimeRangeEnum
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        maxItemsInEachRow = if (timeRangeEnum != TimeRangeEnum.ALL_TIME) 1 else 2
    ) {
        weightTrainingPrData.mostWeightPr?.let {
            PersonalRecordBadges(
                timeRangeOfWorkouts = timeRangeEnum,
                prDataForTimeRange = it,
                modifier = Modifier.weight(1f),
                personalRecordTitleText = stringResource(id = R.string.title_most_weight_lifted)
            )
        }
        if (timeRangeEnum != TimeRangeEnum.ALL_TIME) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Spacing.spacing24)
            )
        }
        weightTrainingPrData.mostVolumePr?.let {
            PersonalRecordBadges(
                timeRangeOfWorkouts = timeRangeEnum,
                prDataForTimeRange = it,
                modifier = Modifier.weight(1f),
                personalRecordTitleText = stringResource(id = R.string.title_most_volume_lifted)
            )
        }
    }
}
