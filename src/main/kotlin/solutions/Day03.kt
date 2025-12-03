package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.Input
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs

fun main() {
    val input = InputUtils.getDayInputText(3)
    val testInput = InputUtils.getTestInputText(3)
    val inputs = Inputs(input, testInput)
    Day03(inputs).run(correctResultPart1 = 357L, correctResultPart2 = 3121910778619L)
}

class Day03(
    override val inputs: Inputs
) : Day(inputs) {
    override fun part1(test: Boolean): Long {
        val input = if (test) inputs.testInput else inputs.input
        val digits = getDigits(input)
        return getJoltage(digits, 2)
    }

    override fun part2(test: Boolean): Long {
        val input = if (test) inputs.testInput else inputs.input
        val digits = getDigits(input)
        return getJoltage(digits, 12)
    }

    fun getDigits(input: Input) = input.inputLines.map {
        it.map { c -> c.digitToInt() }
    }

    fun getJoltage(digits: List<List<Int>>, digitAmount: Int): Long = digits.sumOf {
        val numbers = mutableListOf<Int>()
        for (i in 1..digitAmount) {
            var list = it.dropLast(digitAmount - i)
            numbers.forEach { n ->
                val index = list.indexOf(n)
                list = list.drop(index + 1)
            }
            numbers.add(list.max())
        }
        numbers.joinToString("").toLong()
    }
}