fun main() {
    val filename = "Day08"
    val part1Data = ::part1 to 26
    val part2Data = ::part2 to 61229

    listOf(part1Data, part2Data).forEach { (func, test) ->
        check(func(invokeOnLinesInMemory("${filename}_test", ::prepareInput)) == test)
        println(func(invokeOnLinesInMemory(filename, ::prepareInput)))
    }
}

private fun prepareInput(lines: List<String>): List<Digits> {
    return lines.map { line ->
        line.split(" | ").let {
            Digits(it[0].split(' '), it[1].split(' '))
        }
    }
}

private fun parseSimpleDigit(digit: String): Int? {
    return when (digit.length) {
        2 -> 1
        4 -> 4
        3 -> 7
        7 -> 8
        else -> null
    }
}

private fun part1(digits: List<Digits>): Int {
    return digits.sumOf { digit -> digit.output.count { parseSimpleDigit(it) != null } }
}

fun processLine(digits: Digits): Int {
    lateinit var digit1: HashSet<Char>
    lateinit var digit4: HashSet<Char>
    lateinit var digit7: HashSet<Char>
    lateinit var digit8: HashSet<Char>
    digits.input
        .mapNotNull { parseSimpleDigit(it)?.let { value -> it.toHashSet() to value } }
        .forEach { (digit, value) ->
            when (value) {
                1 -> digit1 = digit
                4 -> digit4 = digit
                7 -> digit7 = digit
                8 -> digit8 = digit
            }
        }
    return digits.output.map {
        val set = it.toHashSet()
        when (set.size) {
            5 -> when {
                set.containsAll(digit7) -> 3
                set.containsAll(digit8 - digit4) -> 2
                else -> 5
            }
            6 -> when ((digit8 - set).first()) {
                in digit1 -> 6
                in digit4 -> 0
                else -> 9
            }
            else -> parseSimpleDigit(it)
        }
    }.joinToString("").toInt()
}

private fun part2(digits: List<Digits>): Int {
    return digits.sumOf(::processLine)
}

class Digits(val input: List<String>, val output: List<String>)