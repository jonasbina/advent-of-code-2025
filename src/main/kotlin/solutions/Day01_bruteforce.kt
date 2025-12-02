package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.testPrintln
import kotlin.math.abs

fun main() {
    val input = InputUtils.getDayInputText(1)
    val testInput = InputUtils.getTestInputText(1)
    val inputs = Inputs(input, testInput)
    Day01_bruteforce(inputs).run(correctResultPart1 = 3, correctResultPart2 = 6)
}

//pretty ugly but works
class Day01_bruteforce(
    override val inputs: Inputs
) : Day(inputs) {
    override fun part1(test: Boolean): Any {
        val input = if (test) inputs.testInput else inputs.input
        var count = 0
        var currentNumber = 50
        val minNumber = 0
        val maxNumber = 99
        input.inputLines.forEach {
            val direction = it.first()
            val rawAmount = it.drop(1).toInt()
            val amount = if (direction == 'L') -rawAmount else rawAmount

            currentNumber = performOverflow(currentNumber, amount, minNumber, maxNumber).first

            if (currentNumber == 0) {
                count++
            }
        }
        return count
    }

    fun performOverflow(currentNumber: Int, amount: Int, minNumber: Int, maxNumber: Int): Pair<Int, Int> {
        val addingAmount = if (amount < 0) -1 else 1
        var number = currentNumber
        var repetitions = 0
        repeat(abs(amount)) {
            number += addingAmount
            if (number < minNumber) {
                number = maxNumber
            }
            if (number > maxNumber) {
                number = minNumber
            }
            if (number==0) repetitions++
        }
        return number to repetitions
    }

    override fun part2(test: Boolean): Any {
        val input = if (test) inputs.testInput else inputs.input
        var count = 0
        var currentNumber = 50
        val minNumber = 0
        val maxNumber = 99
        input.inputLines.forEach {
            val direction = it.first()
            val rawAmount = it.drop(1).toInt()
            val amount = if (direction == 'L') -rawAmount else rawAmount

            val result = performOverflow(currentNumber, amount, minNumber, maxNumber)
            currentNumber = result.first
            count += result.second
        }
        return count
    }
}