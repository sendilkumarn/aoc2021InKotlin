package Day02

fun parseInput(lines: List<String>) = lines.map {
    val pair = it.split(" ")
    Pair(pair.first(), pair.last().toInt())
}

fun part1(lines: List<String>)  {
    var dep = 0
    var fwd = 0
    parseInput(lines).forEach {
        when(it.first) {
            "forward" -> fwd += it.second
            "up" -> dep -= it.second
            "down" -> dep += it.second
            else -> {}
        }
    }
    println(dep * fwd)
}

fun part2(lines: List<String>) {
    var aim = 0
    var dep = 0
    var fwd = 0
    parseInput(lines).forEach {
        when(it.first) {
            "forward" -> {
                fwd += it.second
                dep += (aim * it.second)
            }
            "up" -> aim -= it.second
            "down" -> aim += it.second
            else -> {}
        }
    }
    println(dep * fwd)
}

fun main() {
    val parsedInput = input.lines()
    // Part 1
    part1(parsedInput)
    // Part 2
    part2(parsedInput)
}