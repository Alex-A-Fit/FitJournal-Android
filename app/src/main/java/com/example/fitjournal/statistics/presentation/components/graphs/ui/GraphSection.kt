package com.example.fitjournal.statistics.presentation.components.graphs.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import com.example.fitjournal.core.presentation.theme.Spacing
import com.example.fitjournal.statistics.domain.model.GraphValues
import kotlin.math.roundToInt

@Composable
fun GraphSection(
    graphData: List<GraphValues>?,
    stringForGraphPopUp: (String, Float) -> String
) {
    if (graphData == null) {
        GraphError()
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
                return@labelData "0"
            }
            val scale = (highestPoint.toDouble() / graphData.size.toDouble())
            (i * scale).roundToInt().toString()
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
                        color = MaterialTheme.colorScheme.primary
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
                            stringForGraphPopUp("| Date: ${graphData[x.toInt()].date}", y)
                        }
                    )
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = MaterialTheme.colorScheme.background,
        containerPaddingEnd = Spacing.spacing128
    )
    Spacer(modifier = Modifier.height(Spacing.spacing16))
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp, max = 500.dp),
        lineChartData = lineChartData
    )
}

@Composable
fun GraphError() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing16)
    ) {
        Text(text = "No Data found,")
        Text(text = "Unable to display graph")
    }
}
