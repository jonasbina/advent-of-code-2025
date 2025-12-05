package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.getBasedOnTest
import com.jonasbina.utils.testPrintln

fun main() {
    val input = InputUtils.getDayInputText(5)
    val testInput = InputUtils.getTestInputText(5)
    val inputs = Inputs(input, testInput)
    Day05(inputs).run(correctResultPart1 = 3, correctResultPart2 = 14L)
}

class Day05(
    override val inputs: Inputs
) : Day(inputs) {
    val mapped = inputs.map {
        val takeWhile = it.inputLines.takeWhile { line -> line.isNotBlank() }
        val ranges = takeWhile.map { line -> val split = line.split("-"); split[0].toLong()..split[1].toLong() }
        val numbers = it.inputLines.drop(takeWhile.size + 1).map { line -> line.toLong() }
        ranges to numbers
    }

    override fun part1(test: Boolean): Any {
        val lists = mapped.getBasedOnTest(test)
        return lists.second.count {
            lists.first.any { range -> it in range }
        }
    }

    override fun part2(test: Boolean): Any {
        val ranges = mapped.getBasedOnTest(test).first.toMutableList()
        for (i in ranges.indices) {
            var range = ranges[i]
            for (x in ranges.indices) {
                if (x != i) {
                    var second = ranges[x]
                    if (second.first in range || second.last in range || range.first in second || range.last in second) {
                        if (second.first <= range.first) {
                            if (second.last <= range.last) {
                                range = second.last + 1..range.last
                            } else {
                                range = LongRange.EMPTY
                            }
                        } else {
                            if (second.last <= range.last) {
                                second = LongRange.EMPTY
                            } else {
                                second = range.last + 1..second.last
                            }
                        }
                        ranges[x] = second
                    }
                }
            }
            ranges[i] = range
        }
        return ranges.sumOf {
            if (it == LongRange.EMPTY) 0 else it.last - it.first + 1
        }
    }
}

//only for joined ranges
private fun Set<LongRange>.sum(): LongRange =
    minOf { it.first }..maxOf { it.last }
