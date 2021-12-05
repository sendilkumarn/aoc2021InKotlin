package Day04

class BoardItem(var number: Int, var isMarked: Boolean)
class BoardRow(val boardRow: MutableList<BoardItem>)
class BoardGroup(val boardRows: MutableList<BoardRow>)

class BingoBoard(private val boardGroups: MutableList<BoardGroup>) {
    private var boardIndex: Int? = null
    private val winGroup = mutableSetOf<Int>()

    fun play(turn: Int) {
        boardGroups.forEach { bgs ->
            bgs.boardRows.forEach { board ->
                board.boardRow.forEach {
                    if (it.number == turn) {
                        it.isMarked = true
                    }
                }
            }
        }
    }

    fun isWin(): Boolean {
        boardGroups.forEachIndexed { bIndex, bgs ->
            val colCountList = MutableList(bgs.boardRows.size) { 0 }
            bgs.boardRows.forEach { board ->
                var rowCount = 0
                board.boardRow.forEachIndexed { index, bi ->
                    if (bi.isMarked) {
                        rowCount++
                        colCountList[index]++
                    }
                }

                if (rowCount == board.boardRow.size || colCountList.maxOf { it } == board.boardRow.size) {
                    boardIndex = bIndex
                    return true
                }
            }
        }
        return false
    }

    fun isFullWin(): Boolean {
        boardGroups.forEachIndexed { bIndex, bgs ->
            val colCountList = MutableList(bgs.boardRows.size) { 0 }
            bgs.boardRows.forEach { board ->
                var rowCount = 0
                board.boardRow.forEachIndexed { index, bi ->
                    if (bi.isMarked) {
                        rowCount++
                        colCountList[index]++
                    }
                }

                if (rowCount == board.boardRow.size || colCountList.maxOf { it } == board.boardRow.size) {
                    boardIndex = bIndex
                    winGroup.add(bIndex)
                    if (winGroup.size == boardGroups.size) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun getOutput(currentTurn: Int) {
        if (boardIndex != null) {
            val unmarkedInGroup =
                boardGroups[boardIndex!!].boardRows
                    .flatMap { b ->
                        b.boardRow
                            .filter { bi -> !bi.isMarked}
                            .map { it.number }
                    }
                    .sum()
            println(unmarkedInGroup * currentTurn)
        }
    }
}

fun parseInput(lines: List<String>): Pair<List<Int>, BingoBoard> {
    val gameSequence = lines.first().trim().split(",").map { it.toInt() }

    val gameBoard = lines.subList(2, lines.size)
        .map { line ->
            line.split("\n\n")
                .flatMap {
                    it.split(" ")
                        .filter { ch -> ch.isNotEmpty() }
                        .map { ch -> BoardItem(ch.toInt(), false) }
                }
        }

    val boardGroups = gameBoard.fold(mutableListOf(mutableListOf<BoardRow>())) { acc, boardRow ->
        if (boardRow.isEmpty()) acc.add(mutableListOf())
        else acc.last().add(BoardRow(boardRow.toMutableList()))
        acc
    }.filterNot { it.isEmpty() }.map { BoardGroup(it) }.toMutableList()
    return Pair(gameSequence, BingoBoard(boardGroups))
}

fun main() {
    val lines = input.lines()
    // part 1
    part1(lines)
    // part 2
    part2(lines)
}

fun part1(lines: List<String>) {
    val (gameSequence, bingoBoard) = parseInput(lines)
    gameSequence.forEach { turn ->
        bingoBoard.play(turn)
        if (bingoBoard.isWin()) {
            bingoBoard.getOutput(turn)
            return
        }
    }
}

fun part2(lines: List<String>) {
    val (gameSequence, bingoBoard) = parseInput(lines)
    gameSequence.forEach { turn ->
        bingoBoard.play(turn)
        if (bingoBoard.isFullWin()) {
            bingoBoard.getOutput(turn)
            return
        }
    }
}
