fun main() {
    evaluateDay(
        dayNumber = 10,
        prepareInput = ::prepareInput,
        part1Data = ::part1 to 26397L,
        part2Data = ::part2 to 288957L
    )
}

private fun prepareInput(lines: List<String>): List<String> = lines

private fun Char.getCorruptedScore(): Long {
    return when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> 0
    }
}

private fun Char.getIncompleteScore(): Long {
    return when (this) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> 0
    }
}

private fun Char.isClosing(): Boolean = this in listOf(')', ']', '}', '>')

private fun Char.getClosingChar(): Char {
    return when (this) {
        '(' -> ')'
        '[' -> ']'
        '<' -> '>'
        '{' -> '}'
        else -> '0'
    }
}

private fun validateLine(line: String, onlyCorrupted: Boolean): Long {
    val stack = mutableListOf<Char>()
    line.forEach {
        if (it.isClosing()) {
            if (it != stack.removeLast()) {
                return if (onlyCorrupted) {
                    it.getCorruptedScore()
                } else {
                    -1
                }
            }
        } else {
            stack.add(it.getClosingChar())
        }
    }
    return if (!onlyCorrupted) {
        stack.foldRight(initial = 0L) { c, acc -> acc * 5 + c.getIncompleteScore() }
    } else {
        -1
    }
}

private fun part1(lines: List<String>): Long {
    return lines.map { validateLine(it, true) }.filter { it > 0 }.sum()
}

private fun part2(lines: List<String>): Long {
    return lines.map { validateLine(it, false) }.filter { it > 0 }.sorted().let { it[it.size / 2] }
}