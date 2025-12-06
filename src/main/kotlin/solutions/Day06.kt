package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.getBasedOnTest
import com.jonasbina.utils.testPrintln
import kotlin.math.max

fun main() {
    val input = InputUtils.getDayInputText(6)
    val testInput = InputUtils.getTestInputText(6)
    val inputs = Inputs(input, testInput)
    Day06(inputs).run(correctResultPart1 = 4277556L, correctResultPart2 = 3263827L)
}

class Day06(
    override val inputs: Inputs
) : Day(inputs) {
    val mapped = inputs.map {
        val chars = it.inputLines.last().filter { c -> c != ' ' }
        val numbers = it.inputLines.dropLast(1).map { s ->
            s.split(" ").mapNotNull { str -> if (str.trim().isNotBlank()) str.toInt() else null }
        }
        numbers to chars
    }

    override fun part1(test: Boolean): Any {
        val (numbers, chars) = mapped.getBasedOnTest(test)
        val columns = numbers.flatMap {
            it.mapIndexed { x, n ->
                x to n
            }
        }.groupBy { it.first }

        return columns.toList().sumOf { (column, list) ->
            val char = chars[column]
            var sum = list[0].second.toLong()
            list.drop(1).forEach {
                if (char == '*') {
                    sum *= it.second
                } else {
                    sum += it.second
                }
            }
            sum
        }
    }

    val mapped2 = inputs.map { input ->
        val chars = input.inputLines.last().filter { c -> c != ' ' }
        val groups = mutableMapOf<Int, MutableList<List<Pair<Char, Int>>>>()
        input.inputLines.dropLast(1).forEach { n ->
            var group = 0
            var string = mutableListOf<Pair<Char, Int>>()
            for (i in n.indices) {
                val c = n[i]
                if (string.any { it.first.isDigit() }) {
                    if (c.isDigit()) {
                        string += c to i
                    } else {
                        if (groups.containsKey(group)) {
                            groups[group]!!.add(string)
                        } else {
                            groups[group] = mutableListOf(string)
                        }
                        string = mutableListOf(' ' to i)
                        group++
                    }
                } else {
                    string += c to i
                }
            }
            if (string.isNotEmpty()){
                if (groups.containsKey(group)) {
                    groups[group]!!.add(string)
                } else {
                    groups[group] = mutableListOf(string)
                }
            }
        }
        groups to chars
    }

    override fun part2(test: Boolean): Any {
        val (groups, chars) = mapped2.getBasedOnTest(test)
        return groups.toList().sumOf { (column, list) ->
            val char = chars[column]
            var sum = if (char == '*') 1L else 0L
            val minIndex = list.minOf { it.first().second }
            val maxIndex = list.maxOf { it.last().second }
            for (i in minIndex..maxIndex) {
                var number = ""
                list.forEach { line ->
                    line.forEach { (char, index) ->
                        if (index == i) {
                            number += char
                        }
                    }
                }
                val trimmed = number.trim()
                if (trimmed.isNotBlank()) {
                    if (char == '*') {
                        sum *= trimmed.toLong ()
                    } else {
                        sum += trimmed.toLong()
                    }
                }
            }
            sum
        }
    }
}