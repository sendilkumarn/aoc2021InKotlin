package Day10

import java.util.*

private const val OPEN_PAREN = '('
private const val OPEN_ANGLE = '<'
private const val OPEN_SQUARE = '['
private const val OPEN_BRACKET = '{'

private const val CLOSE_PAREN = ')'
private const val CLOSE_ANGLE = '>'
private const val CLOSE_SQUARE = ']'
private const val CLOSE_BRACKET = '}'

fun parseInput(lines: List<String>) =
    lines

fun part1(lines: List<String>) {
    val parsedInput = parseInput(lines)

    val stack = Stack<Char>()

    val out = parsedInput.sumOf { pairs ->
        pairs.map {
            when (it) {
                OPEN_ANGLE, OPEN_BRACKET, OPEN_PAREN, OPEN_SQUARE -> {
                    stack.push(it)
                    0
                }
                CLOSE_BRACKET -> if (stack.pop() != OPEN_BRACKET) 1197 else 0
                CLOSE_SQUARE -> if (stack.pop() != OPEN_SQUARE) 57 else 0
                CLOSE_PAREN -> if (stack.pop() != OPEN_PAREN) 3 else 0
                CLOSE_ANGLE -> if (stack.pop() != OPEN_ANGLE) 25137 else 0
                else -> 0
            }
        }.sum()
    }

    println("part1: $out")
}

fun part2(lines: List<String>) {
    val parsedInput = parseInput(lines)
    val out = parsedInput.map { pairs ->
        val stack = Stack<Char>()
        val isComplete = pairs.all {
            when (it) {
                OPEN_ANGLE, OPEN_BRACKET, OPEN_PAREN, OPEN_SQUARE -> {
                    stack.push(it)
                    true
                }
                CLOSE_BRACKET -> stack.pop() == OPEN_BRACKET
                CLOSE_SQUARE -> stack.pop() == OPEN_SQUARE
                CLOSE_PAREN -> stack.pop() == OPEN_PAREN
                CLOSE_ANGLE -> stack.pop() == OPEN_ANGLE
                else -> false
            }
        }

        if (isComplete && stack.isNotEmpty()) {
            stack.map {
                when (it) {
                    OPEN_PAREN -> 1
                    OPEN_SQUARE -> 2
                    OPEN_BRACKET -> 3
                    OPEN_ANGLE -> 4
                    else -> 0
                }
            }.reversed().fold(0L) { acc, c ->
                (acc * 5) + c
            }
        } else {
            0
        }
    }
        .filter { it != 0L }
        .sorted()


    println("part2: ${out[(out.size - 1) / 2]}")
}

fun main() {
    val lines = input.lines()
    // part 1
    part1(lines)
    // part 2
    part2(lines)
}