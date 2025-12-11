package com.jonasbina.solutions

import com.jonasbina.utils.Day
import com.jonasbina.utils.InputUtils
import com.jonasbina.utils.Inputs
import com.jonasbina.utils.getBasedOnTest
import kotlin.math.sqrt

fun main() {
    val input = InputUtils.getDayInputText(8)
    val testInput = InputUtils.getTestInputText(8)
    val inputs = Inputs(input, testInput)
    Day08(inputs).run(correctResultPart1 = 40, correctResultPart2 = 25272L)
}

class Day08(
    override val inputs: Inputs
) : Day(inputs) {
    val pointLists = inputs.map {
        it.inputLines.map { line ->
            val ints = line.split(",").map { x -> x.toLong() }
            Point3D(ints[0], ints[1], ints[2])
        }
    }

    //499500
    override fun part1(test: Boolean): Any {
        val howMany = if (test) 10 else 1000
        val points = pointLists.getBasedOnTest(test)
        val map = mutableMapOf<Pair<Point3D, Point3D>, Double>()
        val circuits = mutableListOf<MutableSet<Point3D>>()
        points.forEach { point ->
            points.forEach { point2 ->
                if (point != point2) {
                    val pair = orderedPair(point, point2)
                    if (!map.containsKey(pair)) {
                        map[pair] = point.distTo(point2)
                    }
                }
            }
        }
        val list = map.toList().sortedBy { it.second }.take(howMany).map { it.first }
        for ((p1, p2) in list) {
            val index1 = circuits.indexOfFirst { p1 in it }
            val index2 = circuits.indexOfFirst { p2 in it }
            if (index1 != -1 && index2 != -1) {
                if (index1 != index2) {
                    val a = minOf(index1, index2)
                    val b = maxOf(index1, index2)
                    circuits[a].addAll(circuits[b])
                    circuits.removeAt(b)
                }
            } else if (index1 != -1) {
                circuits[index1].add(p2)
            } else if (index2 != -1) {
                circuits[index2].add(p1)
            } else {
                circuits.add(mutableSetOf(p1, p2))
            }
        }
        val sizes = circuits.map { it.size }.sortedDescending().take(3)
        println(sizes)
        return sizes[0] * sizes[1] * sizes[2]
    }

    override fun part2(test: Boolean): Any {
        val howMany = if (test) 10 else 1000
        val points = pointLists.getBasedOnTest(test)
        val map = mutableMapOf<Pair<Point3D, Point3D>, Double>()
        val circuits = mutableListOf<MutableSet<Point3D>>()
        points.forEach { point ->
            points.forEach { point2 ->
                if (point != point2) {
                    val pair = orderedPair(point, point2)
                    if (!map.containsKey(pair)) {
                        map[pair] = point.distTo(point2)
                    }
                }
            }
        }
        val list = map.toList().sortedBy { it.second }.map { it.first }
        var howManyDone = 0
        for ((p1, p2) in list) {
            howManyDone++
            val index1 = circuits.indexOfFirst { p1 in it }
            val index2 = circuits.indexOfFirst { p2 in it }
            if (index1 != -1 && index2 != -1) {
                if (index1 != index2) {
                    val a = minOf(index1, index2)
                    val b = maxOf(index1, index2)
                    circuits[a].addAll(circuits[b])
                    circuits.removeAt(b)
                }
            } else if (index1 != -1) {
                circuits[index1].add(p2)
            } else if (index2 != -1) {
                circuits[index2].add(p1)
            } else {
                circuits.add(mutableSetOf(p1, p2))
            }
            if (circuits.size == 1 && circuits[0].size == points.size) {
                return p1.x * p2.x
            }
        }
        return 0
    }
}

data class Point3D(val x: Long, val y: Long, val z: Long) {
    fun distTo(other: Point3D): Double {
        val diffX = this.x - other.x
        val diffY = this.y - other.y
        val diffZ = this.z - other.z
        return sqrt((diffX * diffX + diffY * diffY + diffZ * diffZ).toDouble())
    }
}

fun orderedPair(a: Point3D, b: Point3D): Pair<Point3D, Point3D> =
    if (a.x != b.x) {
        if (a.x < b.x) a to b else b to a
    } else if (a.y != b.y) {
        if (a.y < b.y) a to b else b to a
    } else {
        if (a.z <= b.z) a to b else b to a
    }