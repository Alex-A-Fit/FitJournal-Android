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
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
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

@Composable
fun GraphSection(
    graphData: List<Point>?
) {
    if (graphData == null) {
        GraphError()
        return
    }
    val steps = graphData.size
    val pointsData: List<Point> = graphData
    val xAxisData = AxisData.Builder()
        //the distance between each x axis point
        .axisStepSize(Spacing.spacing75)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        //padding between the label and the x axis line
        .labelAndAxisLinePadding(Spacing.spacing16)
        .axisLineColor(MaterialTheme.colorScheme.onPrimary)
        .axisLabelColor(MaterialTheme.colorScheme.primary)
        .axisLabelFontSize(MaterialTheme.typography.bodyLarge.fontSize)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(Spacing.spacing24)
        .labelData { i ->
            val yScale = 900 / steps
            (i * yScale).toString()
        }
        .axisLineColor(MaterialTheme.colorScheme.onPrimary)
        .axisLabelColor(MaterialTheme.colorScheme.primary).build()
    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
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
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(
            color = MaterialTheme.colorScheme.secondary
        ),
        backgroundColor = MaterialTheme.colorScheme.background
    )

    Spacer(modifier = Modifier.height(Spacing.spacing16))
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp, max = 500.dp)
            .padding(horizontal = Spacing.spacing8),
        lineChartData = lineChartData
    )
}

@Composable
fun GraphError() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.spacing16)) {
        Text(text = "No Data found,")
        Text(text = "Unable to display graph")
    }
}