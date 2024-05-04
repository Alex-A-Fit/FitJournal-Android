package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons.AddValueButton
import com.example.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons.SubtractValueButton
import com.example.fitjournal.core.presentation.commoncomponents.textField.DoubleDecimalTextField
import com.example.fitjournal.core.presentation.commoncomponents.textField.IntegerOnlyTextField
import com.example.fitjournal.core.presentation.theme.Spacing

// Here we will have all combinations of
// editing workout values like time, reps, sets, distance, weight

@Composable
fun EditValueSmallIntegerSection(
    onSubtractValueClicked: (String) -> Unit,
    onAddValueClicked: (String) -> Unit,
    onValueChanged: (String) -> Unit,
    workoutPropertyValue: String,
    doesTextFieldHaveError: Boolean
) {
    val value by rememberSaveable(workoutPropertyValue) {
        mutableStateOf(workoutPropertyValue)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        SubtractValueButton(onClick = { onSubtractValueClicked(value) })
        Spacer(modifier = Modifier.width(Spacing.spacing8))
        IntegerOnlyTextField(
            workoutPropertyValue = workoutPropertyValue,
            onValueChanged = { onValueChanged(it) },
            isError = doesTextFieldHaveError
        )
        Spacer(modifier = Modifier.width(Spacing.spacing8))
        AddValueButton(onClick = { onAddValueClicked(value) })
    }
}

@Composable
fun EditValueWeightSection(
    onSubtractValueClicked: (String) -> Unit,
    onAddValueClicked: (String) -> Unit,
    onValueChanged: (String) -> Unit,
    workoutPropertyValue: String,
    doesTextFieldHaveError: Boolean
) {
    val value by rememberSaveable(workoutPropertyValue) {
        mutableStateOf(workoutPropertyValue)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SubtractValueButton(onClick = { onSubtractValueClicked(value) })
            Spacer(modifier = Modifier.width(Spacing.spacing8))
            DoubleDecimalTextField(
                workoutPropertyValue = workoutPropertyValue,
                onValueChanged = { onValueChanged(it) },
                isError = doesTextFieldHaveError
            )
            Spacer(modifier = Modifier.width(Spacing.spacing8))
            AddValueButton(onClick = { onAddValueClicked(value) })
        }
    }
}
