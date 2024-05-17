package com.example.fitjournal.core.presentation.commoncomponents.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.ClearButton
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.EditWorkoutSetProperties
import com.example.fitjournal.core.presentation.commoncomponents.dialogs.components.editWorkoutSet.model.WorkoutTypeDialog
import com.example.fitjournal.core.presentation.theme.DisabledBackgroundGray
import com.example.fitjournal.core.presentation.theme.MediumGray
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.core.presentation.theme.White

// full screen dialog to edit workout sets user inputted
@Composable
fun EditWorkoutSetDialog(
    onDismissRequest: () -> Unit,
    onSaveWorkoutPressed: () -> Unit,
    workoutTypeDialog: WorkoutTypeDialog
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = White,
                    shape = RoundedCornerShape(
                        topStart = Spacing.spacing16,
                        topEnd = Spacing.spacing16
                    )
                )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_cancel),
                contentDescription = stringResource(id = R.string.content_desc_close_dialog_icon),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(Spacing.spacing16)
                    .clickable {
                        onDismissRequest()
                    }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.spacing16)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EditWorkoutSetProperties(workoutTypeDialog = workoutTypeDialog)
                Spacer(modifier = Modifier.height(Spacing.spacing16))
                ClearButton(
                    modifier = Modifier.fillMaxWidth(),
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
                    text = stringResource(id = R.string.button_save_workout),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Spacing.spacing48,
                            vertical = Spacing.spacing32
                        ),
                    textModifier = Modifier.padding(
                        horizontal = Spacing.spacing32,
                        vertical = Spacing.spacing4
                    ),
                    onClick = {
                        // TODO add validation and if valid save else display snackbar on original screen
                        onSaveWorkoutPressed()
                    },
                    buttonColor = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = DisabledBackgroundGray,
                        disabledContentColor = MediumGray
                    )
                )
            }
        }
    }
}
