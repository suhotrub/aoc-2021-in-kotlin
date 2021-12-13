fun main() {
    evaluateDay(
        dayNumber = 11,
        prepareInput = ::prepareInput,
        part1Data = ::part1 to 1656L,
        part2Data = ::part2 to 195L
    )
}

private fun prepareInput(lines: List<String>): List<List<Int>> = lines.map { line -> line.map { it - '0' } }

private fun getAdjacent(centerRow: Int, centerColumn: Int): List<Pair<Int, Int>> {
    return (-1..1).flatMap { row -> (-1..1).map { column -> centerRow + row to centerColumn + column } }
        .filter { (row, column) -> row >= 0 && column >= 0 && row < 10 && column < 10 }
        .filter { (row, column) -> row != centerRow || column != centerColumn }
}

private fun simulateUntil(octopuses: List<List<Int>>, shouldStop: (energy: List<List<Int>>) -> Boolean) {
    val energy = octopuses.map { it.toMutableList() }
    while (true) {
        val flashed = hashSetOf<Pair<Int, Int>>()
        val toFlash = mutableListOf<Pair<Int, Int>>()

        energy.indices.forEach { row ->
            energy[row].indices.forEach { column ->
                if (++energy[row][column] > 9) toFlash.add(row to column)
            }
        }
        while (toFlash.isNotEmpty()) {
            val (row, column) = toFlash.removeFirst()
            if (row to column in flashed) continue
            flashed.add(row to column)
            getAdjacent(row, column).forEach { (aRow, aColumn) ->
                if (++energy[aRow][aColumn] > 9) {
                    toFlash.add(aRow to aColumn)
                }
            }
        }
        flashed.forEach { (row, column) -> energy[row][column] = 0 }
        if (shouldStop(energy)) return
    }
}

private fun part1(octopuses: List<List<Int>>): Long {
    var step = 0
    var flashes = 0L
    simulateUntil(octopuses) {
        flashes += it.sumOf { row -> row.count { octopus -> octopus == 0 } }
        ++step == 100
    }
    return flashes
}

private fun part2(octopuses: List<List<Int>>): Long {
    var step = 0L
    simulateUntil(octopuses) { energy ->
        ++step
        energy.all { column -> column.all { it == 0 } }
    }
    return step
}