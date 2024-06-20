package com.alexafit.fitjournal.statistics.presentation.components.badges

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.statistics.domain.model.PersonalRecordType
import com.alexafit.fitjournal.statistics.domain.model.TimeRangeEnum

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalisthenicsPrBadges(
    calisthenicsPrData: PersonalRecordType.Calisthenics,
    timeRangeEnum: TimeRangeEnum
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        maxItemsInEachRow = if (timeRangeEnum != TimeRangeEnum.ALL_TIME) 1 else 2
    ) {
        calisthenicsPrData.totalRepsPr?.let {
            PersonalRecordBadges(
                timeRangeOfWorkouts = timeRangeEnum,
                prDataForTimeRange = it,
                modifier = Modifier.weight(1f),
                personalRecordTitleText = stringResource(id = R.string.title_total_reps)
            )
        }
        calisthenicsPrData.totalWeightUsedPr?.let {
            if (timeRangeEnum != TimeRangeEnum.ALL_TIME) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Spacing.spacing24)
                )
            }
            PersonalRecordBadges(
                timeRangeOfWorkouts = timeRangeEnum,
                prDataForTimeRange = it,
                modifier = Modifier.weight(1f),
                personalRecordTitleText = stringResource(R.string.title_weight_used)

            )
        }
    }
}
