package com.example.fitjournal.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.R
import com.example.fitjournal.components.cards.CalisthenicsCard
import com.example.fitjournal.components.cards.CardioCard
import com.example.fitjournal.components.cards.WeightLiftingCard
import com.example.fitjournal.components.datepicker.FitJournalDatePickerDialog
import com.example.fitjournal.components.dialogs.FilterJournalDialog
import com.example.fitjournal.model.domain.CalisthenicsModel
import com.example.fitjournal.model.domain.CardioModel
import com.example.fitjournal.model.domain.WeightLiftingModel
import com.example.fitjournal.model.enum.CardioDistanceType
import com.example.fitjournal.model.events.HomeScreenEvents
import com.example.fitjournal.model.state.HomeScreenUiState
import com.example.fitjournal.theme.Spacing

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenUiState,
    snackBarHostState: SnackbarHostState,
    homeScreenEvents: (HomeScreenEvents) -> Unit
) {
    var isDatePickerDialogShowing by rememberSaveable {
        mutableStateOf(homeScreenState.isDatePickerDialogShowing)
    }
    var isFilterDialogShowing by remember {
        mutableStateOf(homeScreenState.isFilterDialogShowing)
    }

    isFilterDialogShowing = homeScreenState.isFilterDialogShowing
    isDatePickerDialogShowing = homeScreenState.isDatePickerDialogShowing
    Box(
        modifier = modifier
    ) {
        if (isDatePickerDialogShowing) {
            FitJournalDatePickerDialog(
                currentDate = homeScreenState.currentDateInMillis,
                selectDate = { selectedDate ->
                    homeScreenEvents(
                        HomeScreenEvents.SelectDateFromDatePicker(
                            userSelectedDate = selectedDate,
                            snackBarHostState = snackBarHostState
                        )
                    )
                },
                dismissDialog = {
                    homeScreenEvents(HomeScreenEvents.DismissDatePicker)
                }
            )
        }

        if (isFilterDialogShowing) {
            FilterJournalDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.spacing12,
                        vertical = Spacing.spacing24
                    )
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(Spacing.spacing16)
                    ),
                properties = DialogProperties(),
                workoutList = homeScreenState.filterDialogList,
                homeScreenEvents = homeScreenEvents
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(all = Spacing.spacing16),
        ) {
            item {
                repeat(1) {
                    CalisthenicsCard(
                        calisthenicsModel = listOf(
                            CalisthenicsModel(
                                reps = 20,
                                time = "3:50"
                            )
                        ),
                        name = "Push-Ups",
                        icon = R.drawable.icon_person
                    )
                    Spacer(modifier = Modifier.height(Spacing.spacing16))
                }
            }

            item {
                repeat(1) {
                    WeightLiftingCard(
                        weightLiftingModel = listOf(
                            WeightLiftingModel(
                                reps = 10,
                                weight = 135.0,
                                time = null
                            )
                        ),
                        name = "Bench Press",
                        icon = R.drawable.icon_dumbell
                    )
                    Spacer(modifier = Modifier.height(Spacing.spacing16))
                }
            }

            item {
                repeat(1) {
                    CardioCard(
                        cardioModel = listOf(
                            CardioModel(
                                distance = 2.0,
                                distanceType = CardioDistanceType.KILOMETERS,
                                time = "3:40",
                                laps = 2.0
                            )
                        ),
                        name = "Biking",
                        icon = R.drawable.icon_sprinting_person
                    )
                    Spacer(modifier = Modifier.height(Spacing.spacing16))
                }
            }

        }
    }
}
