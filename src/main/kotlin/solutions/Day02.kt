package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.Input
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.getBasedOnTest
import com.jonasbina.utils.testPrintln

fun main() {
    val input = InputUtils.getDayInputText(2)
    val testInput = InputUtils.getTestInputText(2)
    val inputs = Inputs(input, testInput)
    Day02(inputs).run(correctResultPart1 = 1227775554L, correctResultPart2 = 4174379265L)
}

//moc nechapu ze to fungovalo na prvni pokus :D
class Day02(
    override val inputs: Inputs
) : Day(inputs) {

    val ranges = inputs.map {
        it.input.split(",").map { str ->
            val split = str.split("-")
            val first = split[0].toLong()
            val second = split[1].toLong()
            first .. second
        }
    }

    override fun part1(test: Boolean): Long {
        return calculateSum(ranges.getBasedOnTest(test))
    }

    override fun part2(test: Boolean): Long {
        return calculateSum(ranges.getBasedOnTest(test), true)
    }

    fun calculateSum(ranges: List<LongRange>, part2: Boolean = false): Long = ranges.sumOf {
        var sum = 0L
        for (n in it) {
            val str = n.toString()
            var added = false
            for (windowSize in 1..str.length / 2) {
                if (!added) {
                    val chunked = str.chunked(windowSize)
                    if (str.length % windowSize == 0 && chunked.toSet().size == 1 && (chunked.size % 2 == 0 || part2)) {
                        sum += n
                        added = true
                    }
                }
            }
        }
        sum
    }
}