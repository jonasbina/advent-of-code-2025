package com.jonasbina.utils

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.measureTime

open class Day(
    open val inputs: Inputs
) {
    open fun part1(test: Boolean = false): Any {
        return 0
    }

    open fun part2(test: Boolean = false): Any {
        return 0
    }

    fun run(correctResultPart1: Any = 0, correctResultPart2: Any) {
        println("=== Starting Tests ===\n")

        executePart("TEST Part 1", correctResultPart1) { part1(true) }
        executePart("TEST Part 2", correctResultPart2) { part2(true) }

        println("=== Tests Ended, Starting Actual Puzzles ===\n")

        executePart("ACTUAL Part 1") { part1() }
        executePart("ACTUAL Part 2") { part2() }

        println("=== End of the Run ===\n")
    }

    private fun executePart(title: String, expected: Any? = null, action: () -> Any) {
        println("$title:")
        var result: Any
        val duration = measureTime {
            result = action()
        }

        println("Execution Time: ${duration.unit()}")
        println("Result: $result")

        if (expected != null) {
            if (result == expected) {
                println("Test Passed!")
            } else {
                println("Test Failed - $expected!=$result")
            }
        }
        println()
    }

    private fun Duration.unit(): String {
        val unit = if (inWholeMilliseconds > 1000) DurationUnit.SECONDS else DurationUnit.MILLISECONDS
        return toString(unit)
    }
}
