package com.example.fitjournal.core.presentation.commoncomponents.customcomponents.editworkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.fitjournal.R
import com.example.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons.AddValueButton
import com.example.fitjournal.core.presentation.commoncomponents.buttons.iconbuttons.SubtractValueButton
import com.example.fitjournal.core.presentation.commoncomponents.textField.DoubleDecimalTextField
import com.example.fitjournal.core.presentation.commoncomponents.textField.IntegerOnlyTextField
import com.example.fitjournal.core.presentation.commoncomponents.textField.TimeOnlyTextField
import com.example.fitjournal.core.presentation.model.enums.EditWorkoutTimeDeterminate
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
            isError = doesTextFieldHaveError,
            modifier = Modifier.fillMaxWidth(.35f)
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

@Composable
fun EditValueTime(
    onValueChanged: (String, EditWorkoutTimeDeterminate) -> Unit,
    hrValue: String,
    minValue: String,
    secValue: String,
    doesTextFieldHaveError: Boolean
) {
    val hourValue by rememberSaveable(hrValue) {
        mutableStateOf(hrValue)
    }
    val minuteValue by rememberSaveable(minValue) {
        mutableStateOf(minValue)
    }
    val secondValue by rememberSaveable(secValue) {
        mutableStateOf(secValue)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(Modifier.weight(2f)) {
            Text(
                text = stringResource(id = R.string.label_hour),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
            TimeOnlyTextField(
                workoutPropertyValue = hourValue,
                onValueChanged = { onValueChanged(it, EditWorkoutTimeDeterminate.HOUR) },
                isError = doesTextFieldHaveError,
                modifier = Modifier.fillMaxWidth(),
                timeDeterminate = EditWorkoutTimeDeterminate.HOUR
            )
        }
        Spacer(modifier = Modifier.width(Spacing.spacing8))

        Text(
            text = ":",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxHeight(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(Spacing.spacing8))
        Column(Modifier.weight(2f)) {
            Text(
                text = stringResource(id = R.string.label_minute),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
            TimeOnlyTextField(
                workoutPropertyValue = minuteValue,
                onValueChanged = { onValueChanged(it, EditWorkoutTimeDeterminate.MINUTE) },
                isError = doesTextFieldHaveError,
                modifier = Modifier.fillMaxWidth(),
                timeDeterminate = EditWorkoutTimeDeterminate.MINUTE
            )
        }
        Spacer(modifier = Modifier.width(Spacing.spacing8))

        Text(
            text = ":",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.width(Spacing.spacing8))
        Column(Modifier.weight(2f)) {
            Text(
                text = stringResource(id = R.string.label_second),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
            TimeOnlyTextField(
                workoutPropertyValue = secondValue,
                onValueChanged = { onValueChanged(it, EditWorkoutTimeDeterminate.SECOND) },
                isError = doesTextFieldHaveError,
                modifier = Modifier.fillMaxWidth(),
                timeDeterminate = EditWorkoutTimeDeterminate.SECOND
            )
        }
    }
}
