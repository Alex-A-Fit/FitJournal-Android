package com.example.fitjournal.commoncomponents.floatingactionbutton

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.fitjournal.R
import com.example.fitjournal.commoncomponents.floatingactionbutton.components.BasicFloatingActionButton
import com.example.fitjournal.theme.Spacing

@Composable
fun AddWorkoutFab(
    showFloatingActionButtonValue: Boolean,
    showFloatingActionButtons: (Boolean) -> Unit
) {
    val threeEightsRotation = 135F
    val revertRotation = 0F
    val animateFabIcon by animateFloatAsState(
        targetValue = if (showFloatingActionButtonValue) threeEightsRotation else revertRotation,
        label = stringResource(id = R.string.label_rotate_Icon),
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 0
        )
    )

    BasicFloatingActionButton(
        modifier = Modifier
            .padding(
                bottom = Spacing.spacing16,
                end = Spacing.spacing16
            ),
        onEvent = {
            showFloatingActionButtons(!showFloatingActionButtonValue)
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_add_circle),
            contentDescription = stringResource(id = R.string.content_desc_add_workout_fab),
            modifier = Modifier
                .size(Spacing.spacing64)
                .padding(Spacing.spacing2)
                .rotate(animateFabIcon)
        )
    }
}
