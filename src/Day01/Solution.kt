package Day01

fun List<Int>.countIncreasingValues() =
    this.filterIndexed { index, i ->
        (index != 0 && i > this[index - 1])
    }.size

fun parseInput(lines: List<String>) = lines.map {
    it.toInt()
}

fun part1(lines: List<String>) {
    println(parseInput(lines).countIncreasingValues())
}

fun part2(lines: List<String>) {
    val parsedInput = parseInput(lines)
    val windowed = parsedInput
        .subList(0, parsedInput.size - 2)
        .mapIndexed { index, i -> i + parsedInput[index + 1] + parsedInput[index + 2] }

    println(windowed.countIncreasingValues())
}

fun main() {
    val parsedInput = input.lines()
    // Part 1
    part1(parsedInput)
    // Part 2
    part2(parsedInput)
}