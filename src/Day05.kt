import kotlin.math.max

fun main() {
    val filename = "Day05"
    val part1Data = ::part1 to 5
    val part2Data = ::part2 to 12

    listOf(part1Data, part2Data).forEach { (func, test) ->
        check(func(invokeOnLinesInMemory("${filename}_test", ::prepareInput)) == test)
        println(func(invokeOnLinesInMemory(filename, ::prepareInput)))
    }
}

private fun prepareInput(lines: List<String>): List<Line> {
    return lines.map { line ->
        line.split(" -> ")
            .map { it.split(',') }
            .flatten()
            .map(String::toInt)
            .let { Line(it[0], it[1], it[2], it[3]) }
    }
}

private fun fillFieldAndCalculateOverlaps(lines: List<Line>): Int {
    var maxX = 0
    var maxY = 0
    lines.forEach {
        maxX = max(maxX, max(it.x1, it.x2))
        maxY = max(maxY, max(it.y2, it.y2))
    }
    val field = Array(maxY + 1) { IntArray(maxX + 1) { 0 } }
    lines.forEach { it.forEach { (x, y) -> field[y][x]++ } }
    return field.sumOf { row -> row.count { it > 1 } }
}

private fun part1(lines: List<Line>): Int {
    return fillFieldAndCalculateOverlaps(lines.filter(Line::isStraight))
}

private fun part2(lines: List<Line>): Int {
    return fillFieldAndCalculateOverlaps(lines)
}

private data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) : Iterable<Pair<Int, Int>> {
    val isStraight = x1 == x2 || y1 == y2

    override fun iterator(): Iterator<Pair<Int, Int>> {
        val dirX = x2.compareTo(x1)
        val dirY = y2.compareTo(y1)
        var x = x1
        var y = y1
        return generateSequence {
            if (x != x2 || y != y2) {
                (x to y).also {
                    x += dirX
                    y += dirY
                }
            } else {
                null
            }
        }.plus(x2 to y2).iterator()
    }
}