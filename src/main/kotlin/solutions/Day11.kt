package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.getBasedOnTest
import kotlin.collections.forEach

fun main() {
    val input = InputUtils.getDayInputText(11)
    val testInput = InputUtils.getTestInputText(11)
    val inputs = Inputs(input, testInput)
    Day11(inputs).run(correctResultPart1 = 5L, correctResultPart2 = 2L)
}

class Day11(
    override val inputs: Inputs
) : Day(inputs) {
    val maps = inputs.map {
        var map = mutableMapOf<String, Set<String>>()
        it.inputLines.forEach { line ->
            val key = line.split(":")[0]
            val values = line.split(":")[1].trim().split(" ").toSet()
            map[key] = values
        }
        map
    }
    val start = "you"
    val end = "out"
    override fun part1(test: Boolean): Any {
        val map = maps.getBasedOnTest(test)
        var paths = mapOf(end to (setOf(end) to 1L))
        var reachedStart = 0L
        while (paths.isNotEmpty()) {
            val newPaths = mutableMapOf<String, Pair<Set<String>, Long>>()
            paths.forEach { (node, pair) ->
                val inputs = map.filter { node in it.value }
                inputs.forEach { input ->
                    if (input.key !in pair.first) {
                        if (input.key == start) {
                            reachedStart += pair.second
                        } else {
                            val item = newPaths[input.key]
                            if (item == null) {
                                newPaths[input.key] = pair.first + input.key to pair.second
                            } else {
                                newPaths[input.key] = item.first + pair.first + input.key to item.second + pair.second
                            }
                        }
                    }
                }
            }
            paths = newPaths
        }
        return reachedStart
    }

    val serverRack = "svr"
    override fun part2(test: Boolean): Any {
        val map = maps.getBasedOnTest(test)
        var paths = mapOf(Triple(serverRack, false, false) to 1L)
        var reachedServer = 0L
        while (paths.isNotEmpty()) {
            val nextPaths = mutableMapOf<Triple<String, Boolean, Boolean>, Long>()
            paths.forEach { (state, count) ->
                val (currentNode, hasDac, hasFft) = state
                val outputs = map[currentNode]

                outputs?.forEach { nextNode ->
                    if (nextNode == end) {
                        if (hasDac && hasFft) {
                            reachedServer += count
                        }
                    } else {
                        val nextDac = hasDac || (nextNode == "dac")
                        val nextFft = hasFft || (nextNode == "fft")
                        val nextState = Triple(nextNode, nextDac, nextFft)

                        nextPaths[nextState] = nextPaths.getOrDefault(nextState, 0L) + count
                    }
                }
            }
            paths = nextPaths
        }
        return reachedServer
    }
}