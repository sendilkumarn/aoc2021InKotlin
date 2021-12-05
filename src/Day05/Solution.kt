package Day05

fun parseInput(lines: List<String>) =
    lines.map { line ->
        line.split(" -> ")
            .filter { it != " -> " }
            .map {
               val point = it.split(",")
                   .map { p -> p.toInt() }
                Pair(point[0], point[1])
            }
    }

fun part1(lines: List<String>) {
    val parsedInput = parseInput(lines)
    val map = mutableMapOf<Pair<Int, Int>, Int>()

    parsedInput.forEach { pairs ->
        val (rowStart, colStart) = pairs[0]
        val (rowEnd, colEnd) = pairs[1]

        val isVertical = rowStart == rowEnd
        val isHorizontal = colStart == colEnd

        val hDir = colStart < colEnd
        val vDir = rowStart < rowEnd

        val vRange = if (hDir) colStart .. colEnd else colStart downTo colEnd
        val hRange = if (vDir) rowStart .. rowEnd else rowStart downTo rowEnd

        if (isHorizontal) {
            hRange.forEach {
                val pair = Pair(it, colEnd)
                map[pair] = map.getOrDefault(pair, 0) + 1
            }
        } else if (isVertical) {
            vRange.forEach {
                val pair = Pair(rowEnd, it)
                map[pair] = map.getOrDefault(pair, 0) + 1
            }
        }
    }

    println(
        map.values.count {
            it > 1
        }
    )
}

fun part2(lines: List<String>) {
    val parsedInput = parseInput(lines)
    val map = mutableMapOf<Pair<Int, Int>, Int>()

    parsedInput.forEach { pairs ->
        val (rowStart, colStart) = pairs[0]
        val (rowEnd, colEnd) = pairs[1]

        val isVertical = rowStart == rowEnd
        val isHorizontal = colStart == colEnd

        val hDir = colStart < colEnd
        val vDir = rowStart < rowEnd

        val vRange = if (hDir) colStart .. colEnd else colStart downTo colEnd
        val hRange = if (vDir) rowStart .. rowEnd else rowStart downTo rowEnd

        if (isHorizontal) {
            hRange.forEach {
                val pair = Pair(it, colEnd)
                map[pair] = map.getOrDefault(pair, 0) + 1
            }
        } else if (isVertical) {
            vRange.forEach {
                val pair = Pair(rowEnd, it)
                map[pair] = map.getOrDefault(pair, 0) + 1
            }
        } else {
            var prev: Pair<Int, Int>? = null

            hRange.forEach { row ->
                vRange.forEach { col ->
                    val condition = if (prev != null) {
                        val colDiff = (col - prev!!.first)
                        val rowDiff = (row - prev!!.second)

                        if (!hDir && !vDir) {
                            colDiff == -1 && rowDiff == -1
                        } else if (!hDir) {
                            colDiff == -1 && rowDiff == 1
                        } else if (!vDir) {
                            colDiff == 1 && rowDiff == -1
                        } else {
                            colDiff == 1 && rowDiff == 1
                        }
                    } else {
                        true
                    }

                    if (condition) {
                        val pair = Pair(col, row)
                        prev = pair
                        map[pair] = map.getOrDefault(pair, 0) + 1
                    }
                }
            }
        }
    }

    println(
        map.values.count {
            it > 1
        }
    )
}

fun main() {
    val lines = input.lines()
    // part 1
    part1(lines)
    // part 2
    part2(lines)
}
