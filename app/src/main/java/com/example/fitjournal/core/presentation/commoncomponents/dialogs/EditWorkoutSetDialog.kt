package com.example.fitjournal.core.presentation.commoncomponents.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.ClearButton
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.EditWorkoutSetProperties
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model.WorkoutTypeDialog
import com.example.fitjournal.core.presentation.theme.DarkGray2
import com.example.fitjournal.core.presentation.theme.DisabledBackgroundGray
import com.example.fitjournal.core.presentation.theme.MediumGray
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.presentation.theme.White

// full screen dialog to edit workout sets user inputted
@Composable
fun EditWorkoutSetDialog(
    onDismissRequest: () -> Unit,
    onSaveWorkoutPressed: (WorkoutTypeDialog) -> Unit,
    workoutTypeDialog: WorkoutTypeDialog
) {
    val isDarkModeOn = isSystemInDarkTheme()
    var isWorkoutValid by rememberSaveable {
        mutableStateOf(false)
    }
    var newUpdatedWorkout by remember {
        mutableStateOf(workoutTypeDialog)
    }
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(.95f)
                .background(
                    color = if (isDarkModeOn) DarkGray2 else White,
                    shape = RoundedCornerShape(
                        topStart = Spacing.spacing16,
                        topEnd = Spacing.spacing16
                    )
                )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(
                                topStart = Spacing.spacing16,
                                topEnd = Spacing.spacing16
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(
                                horizontal = Spacing.spacing8,
                                vertical = Spacing.spacing8
                            )
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.title_edit_set),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .offset(x = Spacing.spacing16),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(Spacing.spacing16))
                        Icon(
                            painter = painterResource(id = R.drawable.icon_circular_cancel),
                            contentDescription = stringResource(id = R.string.content_desc_close_dialog_icon),
                            modifier = Modifier
                                .size(Spacing.spacing32)
                                .clickable {
                                    onDismissRequest()
                                },
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Spacing.spacing16))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Spacing.spacing16
                        )
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EditWorkoutSetProperties(
                        workoutTypeDialog = workoutTypeDialog,
                        isWorkoutValid = { isValidWorkout, workout ->
                            isWorkoutValid = isValidWorkout
                            if (workout != null) {
                                newUpdatedWorkout = workout
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(Spacing.spacing16))
                    ClearButton(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(
                                horizontal = Spacing.spacing32,
                                vertical = Spacing.spacing16
                            ),
                        textModifier = Modifier
                            .padding(
                                horizontal = Spacing.spacing32,
                                vertical = Spacing.spacing4
                            ),
                        onClick = onDismissRequest,
                        text = stringResource(id = R.string.button_cancel_edit)
                    )
                    Spacer(modifier = Modifier.height(Spacing.spacing8))
                    SaveButton(
                        text = stringResource(id = R.string.button_update_workout),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Spacing.spacing32,
                                vertical = Spacing.spacing16
                            ),
                        textModifier = Modifier.padding(
                            horizontal = Spacing.spacing32,
                            vertical = Spacing.spacing4
                        ),
                        onClick = {
                            onSaveWorkoutPressed(newUpdatedWorkout)
                        },
                        buttonColor = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = DisabledBackgroundGray,
                            disabledContentColor = MediumGray
                        ),
                        isEnabled = isWorkoutValid
                    )
                }
            }
        }
    }
}
