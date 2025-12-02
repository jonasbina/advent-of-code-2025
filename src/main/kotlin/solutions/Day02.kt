package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.Input
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
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

    override fun part1(test: Boolean): Long {
        val input = if (test) inputs.testInput else inputs.input
        return calculateSum(input)
    }

    override fun part2(test: Boolean): Long {
        val input = if (test) inputs.testInput else inputs.input
        return calculateSum(input, true)
    }

    fun calculateSum(input: Input, part2: Boolean = false): Long = input.input.split(",").sumOf {
        val split = it.split("-")
        val first = split[0].toLong()
        val second = split[1].toLong()
        var sum = 0L
        for (n in first..second) {
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