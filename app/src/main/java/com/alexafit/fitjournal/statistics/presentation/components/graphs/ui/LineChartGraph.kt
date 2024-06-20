package com.alexafit.fitjournal.statistics.presentation.components.graphs.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.alexafit.fitjournal.R
import com.alexafit.fitjournal.core.presentation.theme.Spacing
import com.alexafit.fitjournal.core.util.constants.Zero
import com.alexafit.fitjournal.statistics.domain.model.GraphValues
import kotlin.math.roundToInt

@Composable
fun LineChartGraph(
    graphData: List<GraphValues>?,
    stringForGraphPopUp: (String, Float) -> String,
    yAxisSuffixLabel: String,
    modifier: Modifier = Modifier,
    convertYaxisValue: (Double) -> Int = { it.roundToInt() }
) {
    if (graphData == null) {
        GraphErrorSection()
        return
    }
    val xAxisData = AxisData.Builder()
        // the distance between each x axis point
        .axisStepSize(Spacing.spacing128)
        .backgroundColor(Color.Transparent)
        .steps(graphData.size - 1)
        .shouldDrawAxisLineTillEnd(true)
        .labelData { i ->
            if (i == 0 && graphData.first().date.isEmpty()) {
                ""
            } else {
                graphData[i].date
            }
        }
        // padding between the label and the x axis line
        .labelAndAxisLinePadding(Spacing.spacing16)
        .axisLineColor(MaterialTheme.colorScheme.onPrimary)
        .axisLabelColor(MaterialTheme.colorScheme.primary)
        .axisLabelFontSize(MaterialTheme.typography.bodyLarge.fontSize)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(graphData.size)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(Spacing.spacing32)
        .labelData { i ->
            val highestPoint = graphData.maxByOrNull { it.point.y }?.point?.y
            if (highestPoint == null) {
                return@labelData Zero.STRING
            }
            val scale = (highestPoint.toDouble() / graphData.size.toDouble())
            val iValue = (i * scale)
            val convertedValue = convertYaxisValue(iValue)
            "$convertedValue $yAxisSuffixLabel"
        }
        .axisLineColor(MaterialTheme.colorScheme.onPrimary)
        .axisLabelColor(MaterialTheme.colorScheme.primary)
        .startDrawPadding(Spacing.spacing8)
        .build()
    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = graphData.map { it.point },
                    LineStyle(
                        color = MaterialTheme.colorScheme.primary,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    SelectionHighlightPoint(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp(
                        popUpLabel = { x, y ->
                            stringForGraphPopUp(
                                "| Date: ${graphData.find { it.point.x == x }?.date ?: "N/A"}",
                                y
                            )
                        }
                    )
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = MaterialTheme.colorScheme.background,
        containerPaddingEnd = Spacing.spacing200
    )
    Spacer(modifier = Modifier.height(Spacing.spacing16))
    LineChart(
        modifier = modifier,
        lineChartData = lineChartData
    )
}

@Composable
fun GraphErrorSection() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Spacing.spacing16,
                vertical = Spacing.spacing16
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_error),
            contentDescription = stringResource(id = R.string.content_desc_error_icon),
            modifier = Modifier.size(Spacing.spacing75)
        )
        Text(
            text = stringResource(id = R.string.error_statistics_graph_not_available),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
