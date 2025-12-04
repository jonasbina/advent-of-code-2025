package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.Point2D
import com.jonasbina.utils.getBasedOnTest
import java.awt.Point

fun main() {
    val input = InputUtils.getDayInputText(4)
    val testInput = InputUtils.getTestInputText(4)
    val inputs = Inputs(input, testInput)
    Day04(inputs).run(correctResultPart1 = 13, correctResultPart2 = 43)
}

class Day04(
    override val inputs: Inputs
) : Day(inputs) {
    val maps = inputs.map {
        it.inputLines.map { str ->
            str.toMutableList()
        }
    }

    override fun part1(test: Boolean): Any {
        val map = maps.getBasedOnTest(test)
        var sum = 0
        map.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '@') {
                    val point = Point2D(x, y)
                    val neighbors = point.neighborsWithDiagonal().filter { it.isInRange(map[0].size, map.size) }
                        .count { map.atPoint(it) == '@' }
                    if (neighbors < 4) {
                        sum++
                    }
                }
            }
        }
        return sum
    }

    // Marginally faster than the last version at triple the complexity, but I like it.
    // (40ms instead of 70ms)
    override fun part2(test: Boolean): Any {
        val map = maps.getBasedOnTest(test)
        val removedPoints = mutableSetOf<Point2D>()
        val mapPoints = mutableMapOf<Point2D, List<Point2D>>()
        map.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '@') {
                    val point = Point2D(x, y)
                    val neighbors = point.neighborsWithDiagonal().filter { it.isInRange(map[0].size, map.size) }
                        .filter { map.atPoint(it) == '@' }
                    if (neighbors.size < 4) {
                        removedPoints.add(point)
                    } else {
                        mapPoints[point] = neighbors
                    }
                }
            }
        }
        while (true){
            val toRemove = mutableListOf<Point2D>()
            mapPoints.forEach { (point, list) ->
                if (list.count { it !in removedPoints } < 4) {
                    removedPoints.add(point)
                    toRemove.add(point)
                }
            }
            toRemove.forEach { point ->
                mapPoints.remove(point)
            }
            if (toRemove.isEmpty()){
                break
            }
        }

        return removedPoints.size
    }
}

fun <R> List<List<R>>.atPoint(point2D: Point2D): R = this[point2D.y][point2D.x]