package Day07

import kotlin.math.abs

fun parseInput(lines: List<String>) =
    lines.flatMap { it.split(",").map { i -> i.toInt() } }

fun part1(lines: List<String>) {
    val parsedInput = parseInput(lines)

    val out = List(parsedInput.size) { index ->
        parsedInput.sumOf {
            abs(it - index)
        }
    }

    println(out.minOrNull())
}

fun part2(lines: List<String>) {
    val parsedInput = parseInput(lines)
    val stepMap = mutableMapOf<Int, Int>()

    val out = List(parsedInput.size) { index ->
        parsedInput.sumOf {
            val step = abs(it - index)

            if (!stepMap.containsKey(step)) {
                stepMap[step] = (0..step).foldIndexed(0) { acc, index, _ ->
                    acc + index
                }.toInt()
            }

            stepMap[step]!!
        }
    }

    println(out.minOrNull())
}

fun main() {
    val lines = input.lines()
    // part 1
    part1(lines)
    // part 2
    part2(lines)
}