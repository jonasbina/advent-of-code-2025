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
    Day01(inputs).run(correctResultPart1 = 3, correctResultPart2 = 6)
}
//pretty ugly but works
class Day01(
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

            currentNumber = performOverflow(currentNumber, amount, minNumber, maxNumber)

            if (currentNumber == 0) {
                count++
            }
        }
        return count
    }

    fun performOverflow(currentNumber: Int, amount: Int, minNumber: Int, maxNumber: Int): Int {
        val actualAmount = amount % 100
        val sum = currentNumber + actualAmount
        if (sum in minNumber..maxNumber) return sum
        if (sum < minNumber) {
            return maxNumber + 1 + sum
        }
        return minNumber + sum % 100
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

            val result = performOverflow2(currentNumber, amount, minNumber, maxNumber)
            currentNumber = result.first
            count += result.second
            if (currentNumber == 0) {
                count++
            }
        }
        return count
    }

    fun performOverflow2(
        currentNumber: Int,
        amount: Int,
        minNumber: Int,
        maxNumber: Int,
    ): Pair<Int, Int> {
        val actualAmount = amount % 100
        val sum = currentNumber + actualAmount
        val fullRotations =  abs(amount / 100)
        if (sum in minNumber..maxNumber) return sum to fullRotations
        if (sum < minNumber) {
            val result = maxNumber + 1 + sum
            val howManyTimes = if (result != 0 && currentNumber != 0) 1 + fullRotations else fullRotations
            return result to howManyTimes
        }
        val result = minNumber + sum % 100
        val howManyTimes = if (result != 0 && currentNumber != 0) 1 + fullRotations else fullRotations
        return result to howManyTimes
    }
}