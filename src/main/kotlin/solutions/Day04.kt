package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.Point2D
import com.jonasbina.utils.getBasedOnTest

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

    override fun part2(test: Boolean): Any {
        val map = maps.getBasedOnTest(test)
        var sum = 0
        while (true) {
            val list = mutableListOf<Point2D>()
            map.forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    if (char == '@') {
                        val point = Point2D(x, y)
                        val neighbors = point.neighborsWithDiagonal().filter { it.isInRange(map[0].size, map.size) }
                            .count { map.atPoint(it) == '@' }
                        if (neighbors < 4) {
                            list.add(point)
                        }
                    }
                }
            }
            if (list.isEmpty()) {
                break
            }
            sum += list.size
            list.forEach {
                map[it.y][it.x] = '.'
            }
        }
        return sum
    }
}

fun <R> List<List<R>>.atPoint(point2D: Point2D): R = this[point2D.y][point2D.x]