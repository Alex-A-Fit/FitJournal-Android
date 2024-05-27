package com.example.fitjournal.core.presentation.commoncomponents.customcomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.ClearButton
import com.example.fitjournal.core.presentation.commoncomponents.buttons.standardbuttons.SaveButton
import com.example.fitjournal.core.presentation.theme.Spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClearAndSaveButtons(
    clearBtnOnClick: () -> Unit,
    saveBtnOnClick: () -> Unit,
    showSaveButton: Boolean = true
) {
    if (showSaveButton) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing16),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ClearButton(
                textModifier = Modifier.padding(
                    horizontal = Spacing.spacing32,
                    vertical = Spacing.spacing4
                ),
                onClick = clearBtnOnClick
            )
            Spacer(modifier = Modifier.width(Spacing.spacing8))
            SaveButton(
                textModifier = Modifier.padding(
                    horizontal = Spacing.spacing32,
                    vertical = Spacing.spacing4
                ),
                text = stringResource(id = R.string.button_save_set),
                onClick = saveBtnOnClick
            )
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing16),
            horizontalArrangement = Arrangement.Center
        ) {
            ClearButton(
                textModifier = Modifier.padding(
                    horizontal = Spacing.spacing32,
                    vertical = Spacing.spacing4
                ),
                onClick = clearBtnOnClick
            )
        }
    }
}
