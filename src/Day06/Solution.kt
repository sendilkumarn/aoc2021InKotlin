package Day06

fun parseInput(lines: List<String>) =
    lines.flatMap {
        it.split(",").map { n -> n.toInt() }
    }

fun solution(inp: List<Int>, days: Int) {
    var list = MutableList(9) { 0L }
    inp.forEach { list[it] = list[it] + 1 }

    repeat(days) {
        val first = list.first()
        list = list.subList(1, list.size)
        list.add(8, first)
        list[6] += first
    }

    println(list.sum())
}

fun part1(lines: List<String>) {
    solution(parseInput(lines), 80)
}

fun part2(lines: List<String>) {
    solution(parseInput(lines), 256)
}

fun main() {
    val lines = input.lines()
    // part 1
    part1(lines)
    // part 2
    part2(lines)
}