package Day03

fun String.at(index: Int) =
    this.split("")
        .filter { it != "" }[index]

fun String.countOneBits() =
    this.filter { it == '1' }.length

fun List<String>.countOneBits(index: Int) =
    this.joinToString("") {
        it.at(index)
    }.countOneBits()

fun String.toBinary() =
    Integer.parseInt(this, 2)

fun List<String>.toBinary() =
        this.joinToString("").toBinary()

fun part1(lines: List<String>) {
    val range = 0 until lines.first().length
    val epsilon = range.map {
        val oneBitCount = lines.countOneBits(it)
        if (oneBitCount > (lines.size - oneBitCount)) "0" else "1"
    }
    val gamma = epsilon.map { if (it == "0") "1" else "0" }
    println(gamma.toBinary() * epsilon.toBinary())
}

fun part2(lines: List<String>) {
    var oxygenList = lines
    var co2List = lines
    val range = 0 until lines.first().length

    range.forEach { index ->
        if (oxygenList.size != 1) {
            val oneBitCount = oxygenList.countOneBits(index)
            oxygenList = oxygenList.filter {
                val identifier = if (oneBitCount < (oxygenList.size - oneBitCount)) "0" else "1"
                it.at(index) == identifier
            }
        }

        if (co2List.size != 1) {
            val oneBitCount = co2List.countOneBits(index)
            co2List = co2List.filter {
                val identifier = if (oneBitCount < (co2List.size - oneBitCount)) "1" else "0"
                it.at(index) == identifier
            }
        }
    }

    println(oxygenList.first().toBinary() * co2List.first().toBinary())
}

fun main() {
    val lines = input.lines()
    // part 1
    part1(lines)
    // part 2
    part2(lines)
}
