package com.alexafit.fitjournal.home.presentation.components.appbar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.commoncomponents.appbars.TopAppBar
import com.alexafit.fitjournal.core.presentation.commoncomponents.icons.HelpIcon
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.home.presentation.components.datepicker.FitJournalDatePicker
import com.alexafit.fitjournal.home.presentation.model.events.HomeAppBarEvents

@Composable
fun HomeTopAppBar(
    homeAppBarEvents: (HomeAppBarEvents) -> Unit,
    currentDate: String
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
            HelpIcon(
                modifier = Modifier.size(Spacing.spacing32),
                contentDescription = stringResource(id = R.string.content_desc_home_screen_help_icon),
                onClick = { homeAppBarEvents(HomeAppBarEvents.ShowHelpDialog()) }
            )
        }
    )
}
