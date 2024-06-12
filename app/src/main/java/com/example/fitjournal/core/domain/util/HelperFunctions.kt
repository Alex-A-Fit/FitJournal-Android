package com.example.fitjournal.core.domain.util

import co.yml.charts.common.model.Point
import com.example.fitjournal.core.util.constants.Constants.STANDARD_DATE_FORMATTER
import com.example.fitjournal.core.util.extensions.roundToTwoDecimalPlaces
import com.example.fitjournal.statistics.domain.model.GraphValues
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

object HelperFunctions {
    fun parseDate(date: String): LocalDate {
        return LocalDate.parse(date, STANDARD_DATE_FORMATTER)
    }

    // filtering graph points to get a total of 7 points to not have too many points in the graph
    // if less than 7 points, return all points
    // if only one point exists, return 2 points, one of em being 0,0
    fun filterPoints(points: List<GraphValues>?): List<GraphValues>? {
        val filteredList = when {
            points.isNullOrEmpty() -> null
            points.size <= 7 -> {
                val newListOfPoints = mutableListOf(
                    GraphValues(
                        point = Point(
                            0F,
                            0F
                        ),
                        ""
                    )
                )
                newListOfPoints.addAll(points)
                newListOfPoints
            }

            else -> {
                val totalPoints = points.size
                val selectedPoints = mutableListOf<GraphValues>()

                selectedPoints.add(points.first())
                val step = (totalPoints - 1) / 6.0

                for (i in 1..5) {
                    val index = (i * step).toInt()
                    selectedPoints.add(points[index])
                }
                selectedPoints.add(points.last())
                return selectedPoints
            }
        }
        if (filteredList.isNullOrEmpty()) return null
        var count = 0
        return filteredList.sortedWith(
            compareBy(
                { if (it.date.isNotEmpty()) parseDate(it.date) else LocalDate.of(1900, 1, 1) },
                { it.point.y }
            )
        ).map {
            val newValue = GraphValues(
                point = Point(
                    count.toFloat(),
                    it.point.y.toDouble().roundToTwoDecimalPlaces().toFloat()
                ),
                date = it.date
            )
            ++count
            newValue
        }
    }
}
