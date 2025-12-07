package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.Point2D
import com.jonasbina.utils.getBasedOnTest

fun main() {
    val input = InputUtils.getDayInputText(7)
    val testInput = InputUtils.getTestInputText(7)
    val inputs = Inputs(input, testInput)
    Day07(inputs).run(correctResultPart1 = 21L, correctResultPart2 = 40L)
}

class Day07(
    override val inputs: Inputs
) : Day(inputs) {
    val maps = inputs.map {
        it.inputLines.map { line -> line.toList() }
    }
    val startPoints = maps.map {
        val x = it[0].indexOf('S')
        Point2D(x, 0)
    }

    override fun part1(test: Boolean): Long {
        val map = maps.getBasedOnTest(test)
        val startPoint = startPoints.getBasedOnTest(test)

        var beams = setOf(startPoint)
        var sum = 0L
        while (beams.isNotEmpty()) {
            val newBeams = mutableSetOf<Point2D>()
            beams.forEach { beam ->
                val down = beam.down()
                val char = map.atPointOrNull(down)
                if (char == '.') {
                    newBeams.add(down)
                }
                if (char == '^') {
                    newBeams.add(down.left())
                    newBeams.add(down.right())
                    sum++
                }
            }
            beams = newBeams
        }
        return sum
    }

    override fun part2(test: Boolean): Long {
        val map = maps.getBasedOnTest(test)
        val startPoint = startPoints.getBasedOnTest(test)

        var beams = setOf(startPoint to 1L)
        var amount = 0L
        while (beams.isNotEmpty()) {
            val newBeams = mutableListOf<Pair<Point2D, Long>>()
            beams.forEach { (beam, amount) ->
                val down = beam.down()
                val char = map.atPointOrNull(down)
                if (char == '.') {
                    newBeams.add(down to amount)
                }
                if (char == '^') {
                    newBeams.add(down.left() to amount)
                    newBeams.add(down.right() to amount)
                }
            }
            beams = newBeams.groupBy { it.first }.map { it.key to it.value.sumOf { value -> value.second } }.toSet()
            if (beams.isNotEmpty()) {
                amount = beams.sumOf { it.second }
            }
        }
        return amount
    }
}

fun <R> List<List<R>>.atPointOrNull(point2D: Point2D): R? = this.getOrNull(point2D.y)?.getOrNull(point2D.x)