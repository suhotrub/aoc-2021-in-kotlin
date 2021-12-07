import java.lang.Integer.min
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

fun main() {
    val filename = "Day07"
    val part1Data = ::part1 to 37
    val part2Data = ::part2 to 168

    listOf(part1Data, part2Data).forEach { (func, test) ->
        check(func(invokeOnLinesInMemory("${filename}_test", ::prepareInput)) == test)
        println(func(invokeOnLinesInMemory(filename, ::prepareInput)))
    }
}

private fun prepareInput(lines: List<String>): List<Int> {
    return lines.first().split(',').map(String::toInt)
}

private fun part1(fishes: List<Int>): Int {
    val median = fishes.sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2.0 }.roundToInt()
    return fishes.sumOf { (it - median).absoluteValue }
}

private fun part2(fishes: List<Int>): Int {
    val mean = fishes.average().toInt()
    val cost = { dist: Int -> dist * (dist + 1) / 2 }
    return min(
        fishes.sumOf { cost((it - mean).absoluteValue) },
        fishes.sumOf { cost((it - mean - 1).absoluteValue) }
    )
}