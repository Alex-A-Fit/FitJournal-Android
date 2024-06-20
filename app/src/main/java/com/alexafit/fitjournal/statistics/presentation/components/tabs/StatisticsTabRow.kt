package com.alexafit.fitjournal.statistics.presentation.components.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.alexafit.fitjournal.core.presentation.theme.LightGray2
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.statistics.domain.model.TimeRangeEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsTabRow(
    timeRangeOfWorkouts: Int,
    getStatsBasedOnTimeSelected: (TimeRangeEnum) -> Unit
) {
    val tabTitles = TimeRangeEnum.entries
    var state by remember(timeRangeOfWorkouts) {
        mutableIntStateOf(timeRangeOfWorkouts)
    }
    Box(
        Modifier
            .padding(Spacing.spacing16)
            .fillMaxWidth()
    ) {
        SecondaryTabRow(
            selectedTabIndex = state,
            indicator = {
                FancyIndicator(
                    modifier = Modifier.tabIndicatorOffset(state)
                )
            },
            containerColor = LightGray2,
            modifier = Modifier.clip(RoundedCornerShape(Spacing.spacing24))
        ) {
            tabTitles.forEachIndexed { index, timeRange ->
                FancyTab(
                    title = stringResource(id = timeRange.stringIdValue),
                    onClick = {
                        state = index
                        getStatsBasedOnTimeSelected(timeRange)
                    },
                    selected = (index == state),
                    modifier = Modifier.zIndex(2f)
                )
            }
        }
    }
}
