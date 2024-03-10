package com.example.fitjournal.commoncomponents.dialogs

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterJournalDialog(
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(),
    dismissEvent: () -> Unit,
    dialogContent: @Composable () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = {
            dismissEvent()
        },
        modifier = modifier,
        properties = properties,
        content = {
            dialogContent()
//            FilterJournalSection(
//                modifier = modifier,
//                workoutList = workoutList,
//                onDismissDialog = {
//                    homeScreenEvents(HomeScreenEvents.DismissFilterExercisesDialog)
//                },
//                onConfirmDialog = {
//                    homeScreenEvents(HomeScreenEvents.OnConfirmFilterExercisesDialog(it))
//                }
//            )
        }
    )
}
