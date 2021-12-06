fun main() {
    val filename = "Day06"
    val part1Data = ::part1 to 5934L
    val part2Data = ::part2 to 26984457539L

    listOf(part1Data, part2Data).forEach { (func, test) ->
        check(func(invokeOnLinesInMemory("${filename}_test", ::prepareInput)) == test)
        println(func(invokeOnLinesInMemory(filename, ::prepareInput)))
    }
}

private fun prepareInput(lines: List<String>): List<Int> {
    return lines.first().split(',').map(String::toInt)
}

private fun findPopulation(fishes: List<Int>, days: Int): Long {
    val fishesAtDays = LongArray(9) { 0 }

    fishes.forEach { fishesAtDays[it]++ }
    for (day in 1..days) {
        var prev = 0L
        for (i in fishesAtDays.indices.reversed()) {
            val cur = fishesAtDays[i]
            fishesAtDays[i] = prev
            prev = cur
        }
        fishesAtDays[6] += prev
        fishesAtDays[8] = prev
    }
    return fishesAtDays.sum()
}

private fun part1(fishes: List<Int>): Long {
    return findPopulation(fishes, 80)
}

private fun part2(fishes: List<Int>): Long {
    return findPopulation(fishes, 256)
}