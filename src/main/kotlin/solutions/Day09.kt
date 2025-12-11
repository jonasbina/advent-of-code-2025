package com.jonasbina.solutions

import com.jonasbina.utils.*
import kotlin.math.abs

fun main() {
    val input = InputUtils.getDayInputText(9)
    val testInput = InputUtils.getTestInputText(9)
    val inputs = Inputs(input, testInput)
    Day09(inputs).run(correctResultPart1 = 50L, correctResultPart2 = 24L)
}

class Day09(
    override val inputs: Inputs
) : Day(inputs) {
    val pointLists = inputs.map {
        it.inputLines.map { line ->
            val list = line.split(",").map { num -> num.toInt() }
            Point2D(list[0], list[1])
        }
    }

    override fun part1(test: Boolean): Any {
        val points = pointLists.getBasedOnTest(test)
        var maxArea = 0L
        points.forEach { point1 ->
            points.forEach { point2 ->
                val xLength = abs(point1.x - point2.x).toLong() + 1
                val yLength = abs(point1.y - point2.y).toLong() + 1
                val area = xLength * yLength
                if (area > maxArea) {
                    maxArea = area
                }
            }
        }
        return maxArea
    }

    //2977920000
    //157780144
    override fun part2(test: Boolean): Any {
        val points = pointLists.getBasedOnTest(test)
        val outline = mutableSetOf<Point2D>()
        points.windowed(2, 1, true) {
            val point = it[0]
            val point2 = it.getOrNull(1)
            if (point2 == null) {
                outline.add(point)
                return@windowed
            }
            if (point.x == point2.x) {
                val range = if (point.y > point2.y) point2.y..point.y else point.y..point2.y
                for (y in range) {
                    outline.add(
                        point.copy(y = y)
                    )
                }
            } else {
                val range = if (point.x > point2.x) point2.x..point.x else point.x..point2.x
                for (x in range) {
                    outline.add(
                        point.copy(x = x)
                    )
                }
            }
        }
        var index = 0
        val done = mutableSetOf<Pair<Point2D, Point2D>>()
        val yIntervals = outline.groupBy { it.y }
            .mapValues { (_, points) -> points.map { it.x }.toRanges() }
        return points.maxOf outer@{ point1 ->
            index++
            if (index % 10 == 0) println("$index/${points.size}")
            var maxArea = 0L
            points.forEach inner@{ point2 ->
                if (point1 != point2) {
                    if (!done.add(orderedPair(point1, point2))) return@inner
                    val xRange = minOf(point1.x, point2.x)..maxOf(point1.x, point2.x)
                    val yRange = minOf(point1.y, point2.y)..maxOf(point1.y, point2.y)
                    var allowed = true
                    for (x in xRange) {
                        if (allowed) {
                            val po = point1.copy(x = x)
                            val po2 = point2.copy(x = x)
                            val bool = listOf(po, po2).any { p ->
                                val intervals = yIntervals[p.y] ?: return@any true
                                if (intervals.any { p.x in it }) return@any false

                                val wallsLeft = intervals.count { it.last < p.x }

                                val isInside = wallsLeft % 2 == 1
                                !isInside
                            }
                            if (bool) {
                                allowed = false
                            }
                        }
                    }
                    for (y in yRange) {
                        if (allowed) {
                            val po = point1.copy(y = y)
                            val po2 = point2.copy(y = y)
                            val bool = listOf(po, po2).any { p ->
                                val intervals = yIntervals[p.y] ?: return@any true
                                if (intervals.any { p.x in it }) return@any false

                                val wallsLeft = intervals.count { it.last < p.x }

                                val isInside = wallsLeft % 2 == 1
                                !isInside
                            }
                            if (bool) {
                                allowed = false
                            }
                        }
                    }
                    val xLength = abs(point1.x - point2.x).toLong() + 1
                    val yLength = abs(point1.y - point2.y).toLong() + 1
                    val area = xLength * yLength
                    if (!allowed) {
                        return@inner
                    }

                    if (area > maxArea) {
                        maxArea = area
                    }
                }
            }
            maxArea
        }
    }
    fun List<Int>.toRanges(): List<IntRange> {
        if (isEmpty()) return emptyList()
        val sorted = sorted()
        val ranges = mutableListOf<IntRange>()
        var start = sorted[0]
        var prev = sorted[0]

        for (i in 1 until sorted.size) {
            val current = sorted[i]
            if (current > prev + 1) {
                ranges.add(start..prev)
                start = current
            }
            prev = current
        }
        ranges.add(start..prev)
        return ranges
    }
}

fun orderedPair(a: Point2D, b: Point2D): Pair<Point2D, Point2D> =
    if (a.x != b.x) {
        if (a.x < b.x) a to b else b to a
    } else {
        if (a.y < b.y) a to b else b to a
    }