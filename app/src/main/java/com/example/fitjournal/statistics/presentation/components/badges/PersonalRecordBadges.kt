package com.example.fitjournal.statistics.presentation.components.badges

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.theme.BlueVariant
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.statistics.domain.model.PersonalRecord
import com.example.fitjournal.statistics.domain.model.TimeRangeEnum

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
                personalRecordDescriptionText = when (timeRangeOfWorkouts) {
                    TimeRangeEnum.WEEK -> "Past Week PR"
                    TimeRangeEnum.MONTH -> "Past Month PR"
                    else -> "Past Year PR"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing8)
                    .background(
                        color = BlueVariant,
                        RoundedCornerShape(Spacing.spacing16)
                    )
                    .weight(1f)
                    .heightIn(min = 100.dp, max = 200.dp)
                    .weight(1f),
                personalRecordTitleText = personalRecordTitleText
            )
            AllTimePrBadge(
                personalRecord = prDataForTimeRange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.spacing8)
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        RoundedCornerShape(Spacing.spacing16)
                    )
                    .heightIn(min = 150.dp, max = 250.dp)
                    .weight(1f),
                personalRecordTitleText = personalRecordTitleText
            )
        } else {
            AllTimePrBadge(
                personalRecord = prDataForTimeRange,
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = Spacing.spacing8)
                    .background(
                        color = MaterialTheme.colorScheme.tertiary,
                        RoundedCornerShape(Spacing.spacing16)
                    )
                    .heightIn(min = 150.dp, max = 250.dp),
                personalRecordTitleText = personalRecordTitleText
            )
        }
    }
}

@Composable
private fun CurrentTimePrBadge(
    personalRecord: PersonalRecord,
    personalRecordTitleText: String,
    personalRecordDescriptionText: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        if (personalRecord.personalRecord == personalRecord.allTimePr) {
            Image(
                painter = painterResource(id = R.drawable.icon_all_time_pr),
                contentDescription = "icon to signify all time pr",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(Spacing.spacing48)
            )
        }
        Column(modifier = Modifier.padding(Spacing.spacing16)) {
            Spacer(modifier = Modifier.height(Spacing.spacing8))
            Text(
                text = personalRecordDescriptionText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecordTitleText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecord.personalRecord,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecord.personalRecordDate,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
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
            contentDescription = "icon to signify all time pr",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(Spacing.spacing48)
        )
        Column(modifier = Modifier.padding(Spacing.spacing16)) {
            Spacer(modifier = Modifier.height(Spacing.spacing8))
            Text(
                text = "All Time PR",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecordTitleText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecord.allTimePr,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Spacing.spacing12))
            Text(
                text = personalRecord.allTimePrDate,
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
