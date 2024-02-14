package com.example.fitjournal.components.appbars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.components.datepicker.FitJournalDatePicker
import com.example.fitjournal.components.icons.FilterIcon
import com.example.fitjournal.model.events.HomeAppBarEvents
import com.example.fitjournal.theme.Spacing

@Composable
fun HomeTopAppBar(
    homeAppBarEvents: (HomeAppBarEvents) -> Unit,
    currentDate: String,
) {
    TopAppBar(
        appBarTitle = {
            FitJournalDatePicker(
                modifier = Modifier,
                getPreviousDate = { homeAppBarEvents(HomeAppBarEvents.GetPreviousDate) },
                getNextDate = { homeAppBarEvents(HomeAppBarEvents.GetNextDate) },
                currentDate = currentDate,
                showDatePickerDialog = { homeAppBarEvents(HomeAppBarEvents.ShowDatePickerDialog()) }
            )
        },
        modifier = Modifier.fillMaxWidth(),
        endAlignedActionIcon = {
            FilterIcon(
                modifier = Modifier.size(Spacing.spacing32),
                contentDescription = stringResource(id = R.string.content_desc_home_screen_filter_icon),
                onClick = { homeAppBarEvents(HomeAppBarEvents.ShowFilterDialog()) }
            )
        }
    )


}