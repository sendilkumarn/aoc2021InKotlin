package Day09

fun parseInput(lines: List<String>) =
    lines.map {
        it.split("")
            .filter{ n -> n != "" }
            .map { n -> n.toInt() }
    }

fun isSurroundedByBiggerNumbers(colIndex: Int, rowIndex: Int, parsedInput: List<List<Int>>, col: Int): Boolean {
    val left = colIndex - 1
    val right = colIndex + 1
    val up = rowIndex - 1
    val down = rowIndex + 1

    val rowSize = parsedInput[rowIndex].size

    val isUp = if (up >= 0) col < parsedInput[up][colIndex] else true
    val isDown = if (down < parsedInput.size) col < parsedInput[down][colIndex] else true
    val isLeft = if (left >= 0) col < parsedInput[rowIndex][left] else true
    val isRight = if (right < rowSize) col < parsedInput[rowIndex][right] else true

    return isUp && isDown && isLeft && isRight
}

fun part1(lines: List<String>) {
    val parsedInput = parseInput(lines)

    val out = parsedInput.flatMapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, col ->
            if(isSurroundedByBiggerNumbers(colIndex, rowIndex, parsedInput, col)) col + 1 else 0
        }
    }.sum()

    println(out)
}

fun part2(lines: List<String>) {
    val parsedInput = parseInput(lines)

    val out = parsedInput.flatMapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, col ->
            if (isSurroundedByBiggerNumbers(colIndex, rowIndex, parsedInput, col)) {
                findBasinsSize(rowIndex, colIndex, parsedInput)
            } else {
                0
            }
        }
    }

    println(
        out.sorted().reversed().take(3).fold(1) {
            acc, i -> i * acc
        }
    )

}

fun findBasinsSize(rowIndex: Int, colIndex: Int, parsedInput: List<List<Int>>): Int {
    val visitedSet = mutableSetOf<Pair<Int, Int>>()
    var list = listOf(Pair(rowIndex, colIndex))

    while(list.isNotEmpty()) {
        list = list.filter { !visitedSet.contains(it) }
            .flatMap { (r, c) ->
                visitedSet.add(Pair(r, c))
                goLeft(r, c, parsedInput) +
                    goRight(r, c, parsedInput) +
                    goUp(r, c, parsedInput) +
                    goDown(r, c, parsedInput)
            }.toMutableList()
    }

    return visitedSet.size
}

fun goLeft(rowIndex: Int, colIndex: Int, parsedInput: List<List<Int>>) =
    (colIndex - 1 downTo  0)
        .takeWhile { parsedInput[rowIndex][it] != 9 }
        .map { Pair(rowIndex, it) }

fun goRight(rowIndex: Int, colIndex: Int, parsedInput: List<List<Int>>) =
    (colIndex + 1 until parsedInput[rowIndex].size)
        .takeWhile { parsedInput[rowIndex][it] != 9 }
        .map { Pair(rowIndex, it) }

fun goUp(rowIndex: Int, colIndex: Int, parsedInput: List<List<Int>>) =
    (rowIndex - 1 downTo  0)
        .takeWhile { parsedInput[it][colIndex] != 9 }
        .map { Pair(it, colIndex) }

fun goDown(rowIndex: Int, colIndex: Int, parsedInput: List<List<Int>>) =
    (rowIndex + 1 until parsedInput.size)
        .takeWhile { parsedInput[it][colIndex] != 9 }
        .map { Pair(it, colIndex) }

fun main() {
    val lines = input.lines()
    // part 1
    part1(lines)
    // part 2
    part2(lines)
}