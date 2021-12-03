import kotlin.math.pow

fun main() {

    fun part1(lines: List<String>): Int {
        val frequencies = IntArray(lines.first().length)
        lines.forEach { s -> s.forEachIndexed { id, ch -> frequencies[id] += if (ch == '0') -1 else 1 } }
        val gamma = frequencies.map { if (it > 0) '1' else '0' }.joinToString("").toInt(2)
        val epsilon = gamma xor (2.0.pow(frequencies.size).toInt() - 1)
        return gamma * epsilon
    }

    fun filterLines(lines: List<String>, mostSignificant: Boolean): String {
        var buffer = lines
        for (i in lines.first().indices) {
            var frequency = 0
            buffer = buffer
                    .onEach { frequency += if (it[i] == '0') -1 else 1 }
                    .filter { if (mostSignificant xor (frequency >= 0)) it[i] == '1' else it[i] == '0' }
            if (buffer.size == 1) break
        }
        return buffer.first()
    }

    fun findOxygen(lines: List<String>): String {
        return filterLines(lines, true)
    }

    fun findCO2(lines: List<String>): String {
        return filterLines(lines, false)
    }

    fun part2(lines: List<String>): Int {
        return findOxygen(lines).toInt(2) * findCO2(lines).toInt(2)
    }

    check(invokeOnLinesInMemory("Day03_test", ::part1) == 198)
    println(invokeOnLinesInMemory("Day03", ::part1))

    check(invokeOnLinesInMemory("Day03_test", ::part2) == 230)
    println(invokeOnLinesInMemory("Day03", ::part2))
}