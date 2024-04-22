package com.example.fitjournal.statistics.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.util.state.UiState
import com.example.fitjournal.statistics.domain.model.StatisticsScreenState

@Composable
fun StatisticsScreen(
    addSingleRealmObj: () -> Unit,
    updateSingleRealmObj: () -> Unit,
    deleteWorkoutEntry: () -> Unit,
    getWorkoutEntryList: () -> Unit,
    statisticsScreenState: StatisticsScreenState
) {
    LaunchedEffect(key1 = statisticsScreenState.workoutList) {
        when (statisticsScreenState.workoutList) {
            UiState.None -> {
                getWorkoutEntryList()
            }
            else -> Unit
        }
    }
    Column {
        Button(
            onClick = { addSingleRealmObj() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Spacing.spacing16)
                .background(Color.Blue, RoundedCornerShape(Spacing.spacing8))
        ) {
            Text(text = "Add new entry to bottom of list")
        }
        Spacer(modifier = Modifier.height(Spacing.spacing16))
        Button(
            onClick = { updateSingleRealmObj() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Spacing.spacing16)
                .background(Color.Blue, RoundedCornerShape(Spacing.spacing8))
        ) {
            Text(text = "Update First Workout Entry")
        }
        Spacer(modifier = Modifier.height(Spacing.spacing16))
        Button(
            onClick = { deleteWorkoutEntry() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Spacing.spacing16)
                .background(Color.Blue, RoundedCornerShape(Spacing.spacing8))
        ) {
            Text(text = "Delete First Workout Entry")
        }
    }
}
