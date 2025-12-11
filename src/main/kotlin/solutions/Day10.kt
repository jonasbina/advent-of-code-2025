package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.getBasedOnTest

fun main() {
    val input = InputUtils.getDayInputText(10)
    val testInput = InputUtils.getTestInputText(10)
    val inputs = Inputs(input, testInput)
    Day10(inputs).run(correctResultPart1 = 7L, correctResultPart2 = 0)
}

class Day10(
    override val inputs: Inputs
) : Day(inputs) {
    val machineLists = inputs.map { input ->
        input.inputLines.map { line ->
            val split = line.split(" ")
            val goal = split[0].removeSurrounding("[", "]").map { c -> c == '#' }
            val buttons = mutableListOf<List<Int>>()
            val joltages = mutableListOf<Int>()
            split.forEach { item ->
                if (item.startsWith("{")) {
                    joltages.addAll(item.removeSurrounding("{", "}").split(",").map { it.toInt() })
                }
                if (item.startsWith("(")) {
                    buttons.add(item.removeSurrounding("(", ")").split(",").map { it.toInt() })
                }
            }
            Machine(goal, buttons, joltages)
        }
    }

    val hex = mapOf(
        'a' to 10,
        'b' to 11,
        'c' to 12,
        'd' to 13,
        'e' to 14,
        'f' to 15
    )

    override fun part1(test: Boolean): Any {
        val machines = machineLists.getBasedOnTest(test)
        return machines.sumOf { machine ->
            var moveAmount = 0L
            val state = machine.goal.toMutableList()
            val indexesToTurnOn = machine.goal.withIndex().filter {
                it.value
            }.map { it.index }
            val indexesToTurnOff = machine.goal.withIndex().filter {
                !it.value
            }.map { it.index }
            val seen = mutableSetOf<List<Boolean>>()
            while (state.any()) {
                if (!seen.add(state.toList())) {
                    println("Loop detected at $state")
                    break
                }
                val leftToTurnOn = indexesToTurnOn.filter { !state[it] }
                val leftToTurnOff = indexesToTurnOff.filter { state[it] }

//
//                val actions = (leftToTurnOn + leftToTurnOff).toSet()
//                val buttons = machine.buttons.mapIndexed { index, it ->
//                    val useful = it.count { num -> num in actions }
//                    val counterproductive = it.size - useful
//                    index to useful-counterproductive
//                }
//                val buttonPressedIndex = buttons.maxBy { it.second }.first
//                val buttonPressed = machine.buttons[buttonPressedIndex]
//                buttonPressed.forEach {
//                    state[it] = !state[it]
//                }
                moveAmount++
            }
            moveAmount
        }
    }

    override fun part2(test: Boolean): Any {
        val input = if (test) inputs.testInput else inputs.input
        return 0
    }
}

data class Machine(
    val goal: List<Boolean>,
    val buttons: List<List<Int>>,
    val joltages: List<Int>
)

private fun executeCombination(
    start: List<Boolean>,
    combination: List<Int>,
    buttons: List<List<Int>>
): List<Boolean> {
    val current = start.toMutableList()
    combination.forEach {
        val button = buttons[it]
        button.forEach { index ->
            current[index] = !current[index]
        }
    }
    return current
}