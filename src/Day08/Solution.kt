package Day08

fun String.splitAndSort() =
    this.split("")
        .filter { dl -> dl != "" }
        .sorted()
        .joinToString("")


fun parseInput(lines: List<String>) =
    lines.map { it.split(" | ") }

fun part1(lines: List<String>) {
    val parsedInput = parseInput(lines)
    val easyDigitCount = parsedInput.sumOf {
        it.last().split(" ")
            .filter { word ->
                word.length == 2 || word.length == 3 || word.length == 4 || word.length == 7
            }.size
    }

    println("part1: $easyDigitCount")
}

fun part2(lines: List<String>) {
    val parsedInput = parseInput(lines)
    val total = parsedInput.sumOf {
        val digitList = findDigits(it.first())
        findValue(it[1], digitList)
    }
    println("part2: $total")
}

fun findValue(str: String, digitList: List<String>): Int {
   return str.split(" ").flatMap {
        val sorted = it.splitAndSort()

        digitList.mapIndexed { index, s ->  if(s == sorted) "$index" else "" }
            .filter { s -> s != "" }
    }
       .joinToString("")
       .toInt()
}

fun findDigits(str: String): List<String> {
    val digitList = Array(10){""}
    val wordList = str.split(" ")

    wordList.forEach {
        if (it.length == 2) {
            digitList[1] = it
        }
        if (it.length == 4) {
            digitList[4] = it
        }
        if (it.length == 7) {
            digitList[8] = it
        }
        if (it.length == 3) {
            digitList[7] = it
        }
    }
    digitList[3] = getThree(wordList, digitList)
    digitList[0] = getZero(wordList, digitList)
    digitList[9] = getNine(wordList, digitList)
    digitList[6] = getSix(wordList, digitList)
    digitList[2] = getTwo(wordList, digitList)
    digitList[5] = getFive(wordList, digitList)

    return digitList.map { it.splitAndSort() }
}

fun getFive(wordList: List<String>, digitList: Array<String>): String {
    return wordList.first { !digitList.contains(it) }
}

fun getTwo(wordList: List<String>, digitList: Array<String>): String {
    val missingChar = ('a'..'g').first { digitList[9].indexOf(it) == -1 }
    return wordList.first { !digitList.contains(it) && it.contains(missingChar) }
}

fun getSix(wordList: List<String>, digitList: Array<String>): String {
    return wordList.first { !digitList.contains(it) && it.length == 6 }
}

fun getThree(wordList: List<String>, digitList: Array<String>): String {
    val one = digitList[1]
    val four = digitList[4]
    val commonDigits = four.filter { one.indexOf(it) != -1 }

    return wordList.first {
        it.length == 5 && commonDigits.all { cd -> it.indexOf(cd) != -1 }
    }
}

fun getNine(wordList: List<String>, digitList: Array<String>): String {
    val one = digitList[1]
    val four = digitList[4]
    val commonDigits = four.filter { one.indexOf(it) != -1 }

   return wordList.first { it.length == 6 && commonDigits.all { cd -> it.indexOf(cd) != -1 } && !digitList.contains(it) }
}

fun getZero(wordList: List<String>, digitList: Array<String>): String {
    val three = digitList[3]
    val eight = digitList[8]
    val seven = digitList[7]
    val sideDigits = eight.filter { three.indexOf(it) == -1 } + seven

    return wordList.first {
        it.length == 6 && sideDigits.all { sd -> it.indexOf(sd) != -1 }
    }
}

fun main() {
    val lines = input.lines()
    // part 1
    part1(lines)
    // part 2
    part2(lines)
}