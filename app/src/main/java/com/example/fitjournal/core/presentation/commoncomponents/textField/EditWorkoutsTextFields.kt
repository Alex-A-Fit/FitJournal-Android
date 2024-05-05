package com.example.fitjournal.core.presentation.commoncomponents.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.core.text.isDigitsOnly

// Checks for up to 4 digits are inputted
// with an optional dot for decimal number
// as long as max numbers are not reached.
const val REGEX_DECIMAL_PATTERN = """^(\d){0,4}(\.)?([0-9]{1})?${'$'}"""

@Composable
fun DoubleDecimalTextField(
    workoutPropertyValue: String,
    isError: Boolean,
    onValueChanged: (String) -> Unit
) {
    var value by rememberSaveable(workoutPropertyValue) {
        mutableStateOf(workoutPropertyValue)
    }
    TextField(
        value = value,
        onValueChange = {
            if (it.contains(
                    Regex(pattern = REGEX_DECIMAL_PATTERN)
                )
            ) {
                onValueChanged(it)
                value = it
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth(.5F),
        singleLine = true,
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.onTertiary,
            textAlign = TextAlign.Center
        ),
        isError = isError
    )
}

@Composable
fun IntegerOnlyTextField(
    workoutPropertyValue: String,
    isError: Boolean,
    onValueChanged: (String) -> Unit
) {
    var value by rememberSaveable(workoutPropertyValue) {
        mutableStateOf(workoutPropertyValue)
    }
    TextField(
        value = value,
        onValueChange = {
            if (it.isDigitsOnly() && it.length < 4) {
                if (it.isNotEmpty() && it[0] == '0') return@TextField
                onValueChanged(it)
                value = it
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth(.35F),
        singleLine = true,
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.onTertiary,
            textAlign = TextAlign.Center
        ),
        isError = isError
    )
}
