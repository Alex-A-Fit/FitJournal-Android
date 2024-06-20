package com.alexafit.fitjournal.statistics.presentation.components.badges

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.AllTimePrBadgeColor
import com.alexafit.fitjournal.core.presentation.theme.CurrentPrBadgeColor
import com.alexafit.fitjournal.core.presentation.theme.DarkGray2
import com.alexafit.fitjournal.core.presentation.theme.LightGray
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.statistics.domain.model.PersonalRecord
import com.alexafit.fitjournal.statistics.domain.model.TimeRangeEnum

@Composable
fun PersonalRecordBadges(
    timeRangeOfWorkouts: TimeRangeEnum,
    personalRecordTitleText: String,
    prDataForTimeRange: PersonalRecord,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = if (timeRangeOfWorkouts == TimeRangeEnum.ALL_TIME) {
            Arrangement.Center
        } else {
            Arrangement.SpaceAround
        }
    ) {
        if (timeRangeOfWorkouts != TimeRangeEnum.ALL_TIME) {
            CurrentTimePrBadge(
                personalRecord = prDataForTimeRange,
                personalRecordTimeRangeTitleText = when (timeRangeOfWorkouts) {
                    TimeRangeEnum.WEEK -> stringResource(id = R.string.title_past_week_pr)
                    TimeRangeEnum.MONTH -> stringResource(id = R.string.title_past_month_pr)
                    else -> stringResource(id = R.string.title_past_year_pr)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing8)
                    .background(
                        color = CurrentPrBadgeColor,
                        RoundedCornerShape(Spacing.spacing16)
                    )
                    .weight(1f)
                    .heightIn(min = Spacing.spacing150, max = Spacing.spacing250),
                personalRecordTitleText = personalRecordTitleText
            )
            AllTimePrBadge(
                personalRecord = prDataForTimeRange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing8)
                    .background(
                        color = AllTimePrBadgeColor,
                        RoundedCornerShape(Spacing.spacing16)
                    )
                    .heightIn(
                        min = Spacing.spacing150,
                        max = Spacing.spacing250
                    )
                    .weight(1f),
                personalRecordTitleText = personalRecordTitleText
            )
        } else {
            AllTimePrBadge(
                personalRecord = prDataForTimeRange,
                modifier = Modifier
                    .width(Spacing.spacing200)
                    .padding(horizontal = Spacing.spacing8)
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(Spacing.spacing16)
                    )
                    .heightIn(
                        min = Spacing.spacing150,
                        Spacing.spacing250
                    ),
                personalRecordTitleText = personalRecordTitleText
            )
        }
    }
}

@Composable
private fun CurrentTimePrBadge(
    personalRecord: PersonalRecord,
    personalRecordTitleText: String,
    personalRecordTimeRangeTitleText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        if (personalRecord.personalRecord == personalRecord.allTimePr) {
            Image(
                painter = painterResource(id = R.drawable.icon_all_time_pr),
                contentDescription = stringResource(id = R.string.content_desc_all_time_pr_icon),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(Spacing.spacing48)
            )
        }
        Column(modifier = Modifier.padding(Spacing.spacing16)) {
            Spacer(modifier = Modifier.height(Spacing.spacing8))
            Text(
                text = personalRecordTimeRangeTitleText,
                style = MaterialTheme.typography.bodyMedium,
                color = LightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecordTitleText,
                style = MaterialTheme.typography.bodyMedium,
                color = LightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecord.personalRecord,
                style = MaterialTheme.typography.headlineSmall,
                color = LightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecord.personalRecordDate,
                style = MaterialTheme.typography.bodyMedium,
                color = LightGray,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AllTimePrBadge(
    personalRecord: PersonalRecord,
    personalRecordTitleText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_all_time_pr),
            contentDescription = stringResource(id = R.string.content_desc_all_time_pr_icon),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(Spacing.spacing48)
        )
        Column(modifier = Modifier.padding(Spacing.spacing16)) {
            Spacer(modifier = Modifier.height(Spacing.spacing8))
            Text(
                text = stringResource(id = R.string.title_all_time_pr),
                style = MaterialTheme.typography.bodyMedium,
                color = DarkGray2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecordTitleText,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkGray2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecord.allTimePr,
                style = MaterialTheme.typography.headlineSmall,
                color = DarkGray2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecord.allTimePrDate,
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = DarkGray2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
